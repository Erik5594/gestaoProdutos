package com.kilowats.controlador;

import static com.kilowats.util.Utils.isNotNullOrEmpty;
import static com.kilowats.util.Utils.mascaraPrimefacesCnpjOuCpf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.kilowats.entidades.Cidade;
import com.kilowats.entidades.EmailFornecedor;
import com.kilowats.entidades.EnderecoFornecedor;
import com.kilowats.entidades.Fornecedor;
import com.kilowats.entidades.TelefoneFornecedor;
import com.kilowats.enuns.TipoPessoa;
import com.kilowats.enuns.TipoTelefoneEnum;
import com.kilowats.servicos.ServicosEmail;
import com.kilowats.servicos.ServicosEndereco;
import com.kilowats.servicos.ServicosFornecedor;
import com.kilowats.servicos.ServicosTelefone;
import com.kilowats.util.jsf.FacesUtils;

@Named
@ViewScoped
public class CadastroFornecedorControlador implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Fornecedor empresa;
	@Inject
	private EnderecoFornecedor endereco;
	@Inject
	private TelefoneFornecedor telefone;
	@Inject
	private Cidade cidade;
	@Inject
	private EmailFornecedor email;
	@Inject
	private EmailFornecedor emailSelecionado;
	@Inject
	private TelefoneFornecedor telefoneSelecionado;
	@Inject
	private ServicosEmail servicosEmail;
	@Inject
	private ServicosTelefone servicosTelefone;
	@Inject
	private ServicosEndereco servicosEndereco;
	@Inject
	private ServicosFornecedor servicosFornecedor;
	
	private List<TelefoneFornecedor> telefones = new ArrayList<>();
	private List<EmailFornecedor> emails = new ArrayList<>();
	private int tpPessoa;
	
	private final String TITULO = "Cadastro Fornecedor: ";
	private final String ERRO_INTERNO = "Erro interno: erro interno contate a administração do sistema!";
	
	public String carregaMascaraCnpjOuCpfPrimefaces(){
		return mascaraPrimefacesCnpjOuCpf(tpPessoa);
	}
	
	public void adcionaTelefone(){
		telefone.setTipoTelefone(TipoTelefoneEnum.COMERCIAL);
		if(servicosTelefone.telefoneIsValido(telefone, TITULO, true)){
			adcionaTelefoneList(this.telefone);
		}
		this.telefone= new TelefoneFornecedor(); 
	}
	
	public void adcionaTelefoneList(TelefoneFornecedor telefone){
		if(telefones.isEmpty()){
			telefones = new ArrayList<>();
		}else{
			if(telefones.contains(telefone)){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validação Telefone: Numero já adicionado!", null));
				return;
			}
		}
		telefones.add(telefone);
	}

	public void adcionaEmail(){
		if(servicosEmail.emailIsValido(email, TITULO, true)){
			adcionaEmailList(this.email);
		}
		this.email = new EmailFornecedor(); 
	}
	
	public void adcionaEmailList(EmailFornecedor email){
		if(emails.isEmpty()){
			emails = new ArrayList<>();
		}else{
			if(emails.contains(email)){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Validação Email: E-mail já adicionado!", null));
				return;
			}
		}
		emails.add(email);
	}

	public void salvar(){
		endereco.getCep().setCidade(cidade);
		if(validacoes()){
			completarDadosEmpresa();
			empresa = servicosFornecedor.persistirFornecedor(this.empresa);
			if(isNotNullOrEmpty(empresa) && empresa.getId() > 0L){
				FacesUtils.sendMensagemOk(TITULO, "Fornecedor cadastrado com suceso!");
			}else{
				FacesUtils.sendMensagemOk(TITULO, ERRO_INTERNO);
			}
		}
	}

	private void completarDadosEmpresa() {
		this.endereco.getCep().setCidade(this.cidade);
		List<EnderecoFornecedor> enderecos = new ArrayList<>();
		enderecos.add(endereco);
		this.empresa.setEndereco(enderecos);
		if(this.tpPessoa == 0){
			this.empresa.setFisicaJuridica(TipoPessoa.FISICA);
		}else{
			this.empresa.setFisicaJuridica(TipoPessoa.JURIDICA);
		}
		if (!this.telefones.isEmpty()) {
			this.empresa.setTelefones(this.telefones);
		}
		if (!this.emails.isEmpty()) {
			this.empresa.setEmails(this.emails);
		}
	}

	private boolean validarEndereco() {
		return servicosEndereco.enderecoIsValido(endereco, TITULO, true);
	}
	
	public boolean validacoes(){
		if(validarDadosPrincipais() & validarEndereco()){
			return true;
		}
		return false;
	}
	
	private boolean validarDadosPrincipais() {
		return servicosFornecedor.validarFornecedor(empresa, TITULO, true);
	}
	
	public List<String> carregarEstados(){
		List<String> ufs = new ArrayList<>();
		ufs.add("GO");
		ufs.add("SP");
		ufs.add("MG");
		ufs.add("RJ");
		return ufs;
	}
	
	public List<String> carregarCidades() {
		List<String> cidades = new ArrayList<>();
		if (cidade != null
				&& (cidade.getUf() != null && !"".equals(cidade.getUf()))) {
			String uf = cidade.getUf();
			if (uf.toUpperCase().equals("GO")) {
				cidades.add("Goiânia");
				cidades.add("Aparecida de Goiânia");
				cidades.add("Trindade");
			}

			if (uf.toUpperCase().equals("SP")) {
				cidades.add("São Paulo");
				cidades.add("Santos");
				cidades.add("Guarulhos");
			}

			if (uf.toUpperCase().equals("MG")) {
				cidades.add("Belo Horizonte");
				cidades.add("Formiga");
				cidades.add("Teófilo Otoni");
			}

			if (uf.toUpperCase().equals("RJ")) {
				cidades.add("Rio de Janeiro");
				cidades.add("Macae");
				cidades.add("Cabo Frio");
			}
		}
		return cidades;
	}

	public Fornecedor getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Fornecedor empresa) {
		this.empresa = empresa;
	}
	public EnderecoFornecedor getEndereco() {
		return endereco;
	}
	public void setEndereco(EnderecoFornecedor endereco) {
		this.endereco = endereco;
	}
	public TelefoneFornecedor getTelefone() {
		return telefone;
	}
	public void setTelefone(TelefoneFornecedor telefone) {
		this.telefone = telefone;
	}
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	public List<TelefoneFornecedor> getTelefones() {
		return telefones;
	}
	public void setTelefones(List<TelefoneFornecedor> telefones) {
		this.telefones = telefones;
	}
	public TelefoneFornecedor getTelefoneSelecionado() {
		return telefoneSelecionado;
	}
	public void setTelefoneSelecionado(TelefoneFornecedor telefoneSelecionado) {
		this.telefoneSelecionado = telefoneSelecionado;
	}
	public EmailFornecedor getEmail() {
		return email;
	}
	public void setEmail(EmailFornecedor email) {
		this.email = email;
	}
	public EmailFornecedor getEmailSelecionado() {
		return emailSelecionado;
	}
	public void setEmailSelecionado(EmailFornecedor emailSelecionado) {
		this.emailSelecionado = emailSelecionado;
	}
	public List<EmailFornecedor> getEmails() {
		return emails;
	}
	public void setEmails(List<EmailFornecedor> emails) {
		this.emails = emails;
	}
	public int getTpPessoa() {
		return tpPessoa;
	}
	public void setTpPessoa(int tpPessoa) {
		this.tpPessoa = tpPessoa;
	}
}
