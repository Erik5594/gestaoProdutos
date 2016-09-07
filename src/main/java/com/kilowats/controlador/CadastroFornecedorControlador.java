package com.kilowats.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.kilowats.entidades.Cidade;
import com.kilowats.entidades.Email;
import com.kilowats.entidades.Empresa;
import com.kilowats.entidades.Endereco;
import com.kilowats.entidades.TelefoneCliente;
import com.kilowats.enuns.TipoPessoa;
import com.kilowats.enuns.TipoTelefoneEnum;
import com.kilowats.servicos.ServicosEmail;
import com.kilowats.servicos.ServicosEndereco;
import com.kilowats.servicos.ServicosFornecedor;
import com.kilowats.servicos.ServicosTelefone;
import com.kilowats.util.Utils;
import com.kilowats.util.jsf.FacesUtils;

@Named
@ViewScoped
public class CadastroFornecedorControlador implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Empresa empresa;
	@Inject
	private Endereco endereco;
	@Inject
	private TelefoneCliente telefone;
	@Inject
	private Cidade cidade;
	@Inject
	private Email email;
	@Inject
	private Email emailSelecionado;
	@Inject
	private TelefoneCliente telefoneSelecionado;
	@Inject
	private ServicosEmail servicosEmail;
	@Inject
	private ServicosTelefone servicosTelefone;
	@Inject
	private ServicosEndereco servicosEndereco;
	@Inject
	private ServicosFornecedor servicosFornecedor;
	
	private List<TelefoneCliente> telefones = new ArrayList<>();
	private List<Email> emails = new ArrayList<>();
	private int tpPessoa;
	
	private final String TITULO = "Cadastro Fornecedor: ";
	private final String ERRO_INTERNO = "Erro interno: erro interno contate a administração do sistema!";
	
	public String carregaMascaraCnpjOuCpfPrimefaces(){
		return Utils.mascarPrimefacesCnpjOuCpf(tpPessoa);
	}
	
	public void adcionaTelefone(){
		telefone.setTipoTelefone(TipoTelefoneEnum.COMERCIAL);
		if(servicosTelefone.telefoneIsValido(telefone, TITULO, true)){
			adcionaTelefoneList(this.telefone);
		}
		this.telefone= new TelefoneCliente(); 
	}
	
	public void adcionaTelefoneList(TelefoneCliente telefone){
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
		this.email = new Email(); 
	}
	
	public void adcionaEmailList(Email email){
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
			if(servicosFornecedor.persistirFornecedor(this.empresa)){
				FacesUtils.sendMensagemOk(TITULO, "Fornecedor cadastrado com suceso!");
			}else{
				FacesUtils.sendMensagemOk(TITULO, ERRO_INTERNO);
			}
		}
	}

	private void completarDadosEmpresa() {
		this.endereco.getCep().setCidade(this.cidade);
		List<Endereco> enderecos = new ArrayList<>();
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

	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	public TelefoneCliente getTelefone() {
		return telefone;
	}
	public void setTelefone(TelefoneCliente telefone) {
		this.telefone = telefone;
	}
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	public List<TelefoneCliente> getTelefones() {
		return telefones;
	}
	public void setTelefones(List<TelefoneCliente> telefones) {
		this.telefones = telefones;
	}
	public TelefoneCliente getTelefoneSelecionado() {
		return telefoneSelecionado;
	}
	public void setTelefoneSelecionado(TelefoneCliente telefoneSelecionado) {
		this.telefoneSelecionado = telefoneSelecionado;
	}
	public Email getEmail() {
		return email;
	}
	public void setEmail(Email email) {
		this.email = email;
	}
	public Email getEmailSelecionado() {
		return emailSelecionado;
	}
	public void setEmailSelecionado(Email emailSelecionado) {
		this.emailSelecionado = emailSelecionado;
	}
	public List<Email> getEmails() {
		return emails;
	}
	public void setEmails(List<Email> emails) {
		this.emails = emails;
	}
	public int getTpPessoa() {
		return tpPessoa;
	}
	public void setTpPessoa(int tpPessoa) {
		this.tpPessoa = tpPessoa;
	}
}
