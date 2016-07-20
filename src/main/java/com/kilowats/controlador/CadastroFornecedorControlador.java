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
import com.kilowats.entidades.Emails;
import com.kilowats.entidades.Empresa;
import com.kilowats.entidades.Endereco;
import com.kilowats.entidades.Telefone;
import com.kilowats.enuns.TipoPessoa;
import com.kilowats.enuns.TipoTelefoneEnum;
import com.kilowats.servicos.ServicosEmail;
import com.kilowats.servicos.ServicosEndereco;
import com.kilowats.servicos.ServicosFornecedor;
import com.kilowats.servicos.ServicosTelefone;
import com.kilowats.utils.Utils;
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
	private Telefone telefone;
	@Inject
	private Cidade cidade;
	@Inject
	private Emails email;
	@Inject
	private Emails emailSelecionado;
	@Inject
	private Telefone telefoneSelecionado;
	@Inject
	private ServicosEmail servicosEmail;
	@Inject
	private ServicosTelefone servicosTelefone;
	@Inject
	private ServicosEndereco servicosEndereco;
	@Inject
	private ServicosFornecedor servicosFornecedor;
	
	private List<Telefone> telefones = new ArrayList<>();
	private List<Emails> emails = new ArrayList<>();
	private int tpPessoa;
	
	private final String TITULO = "Cadastro Fornecedor: ";
	private final String ERRO_INTERNO = "Erro interno: erro interno contate a administração do sistema!";
	
	public String carregaMascaraCnpjOuCpfPrimefaces(){
		return Utils.mascarPrimefacesCnpjOuCpf(tpPessoa);
	}
	
	public void adcionaTelefone(){
		telefone.setTipoTelefone(TipoTelefoneEnum.COMERCIAL);
		if(servicosTelefone.telefoneIsValido(telefone, TITULO)){
			adcionaTelefoneList(this.telefone);
		}
		this.telefone= new Telefone(); 
	}
	
	public void adcionaTelefoneList(Telefone telefone){
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
		if(servicosEmail.emailIsValido(email, TITULO)){
			adcionaEmailList(this.email);
		}
		this.email = new Emails(); 
	}
	
	public void adcionaEmailList(Emails email){
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
		endereco.setCidade(cidade);
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
		this.endereco.setCidade(this.cidade);
		this.empresa.setEndereco(this.endereco);
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
		return servicosEndereco.enderecoIsValido(endereco, TITULO);
	}
	
	public boolean validacoes(){
		if(validarDadosPrincipais() & validarEndereco()){
			return true;
		}
		return false;
	}
	
	private boolean validarDadosPrincipais() {
		return servicosFornecedor.validarFornecedor(empresa, TITULO);
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
	public Telefone getTelefone() {
		return telefone;
	}
	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	public List<Telefone> getTelefones() {
		return telefones;
	}
	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}
	public Telefone getTelefoneSelecionado() {
		return telefoneSelecionado;
	}
	public void setTelefoneSelecionado(Telefone telefoneSelecionado) {
		this.telefoneSelecionado = telefoneSelecionado;
	}
	public Emails getEmail() {
		return email;
	}
	public void setEmail(Emails email) {
		this.email = email;
	}
	public Emails getEmailSelecionado() {
		return emailSelecionado;
	}
	public void setEmailSelecionado(Emails emailSelecionado) {
		this.emailSelecionado = emailSelecionado;
	}
	public List<Emails> getEmails() {
		return emails;
	}
	public void setEmails(List<Emails> emails) {
		this.emails = emails;
	}
	public int getTpPessoa() {
		return tpPessoa;
	}
	public void setTpPessoa(int tpPessoa) {
		this.tpPessoa = tpPessoa;
	}
}
