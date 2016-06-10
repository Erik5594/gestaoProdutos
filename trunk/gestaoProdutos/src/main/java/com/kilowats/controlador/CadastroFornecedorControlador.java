package com.kilowats.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.kilowats.dao.FornecedorDao;
import com.kilowats.entidades.Cidade;
import com.kilowats.entidades.Emails;
import com.kilowats.entidades.Empresa;
import com.kilowats.entidades.Endereco;
import com.kilowats.entidades.Telefone;
import com.kilowats.enuns.TipoTelefoneEnum;
import com.kilowats.interfaces.IValidacaoCadastro;
import com.kilowats.utils.Utils;
import com.kilowats.validadores.ValidacaoCadastroEmail;
import com.kilowats.validadores.ValidacaoCadastroEndereco;
import com.kilowats.validadores.ValidacaoCadastroFornecedor;
import com.kilowats.validadores.ValidacaoCadastroTelefone;

@ManagedBean
@ViewScoped
public class CadastroFornecedorControlador implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Empresa empresa = new Empresa();
	private Endereco endereco = new Endereco();
	private Telefone telefone = new Telefone();
	private Cidade cidade = new Cidade();
	private List<Telefone> telefones = new ArrayList<>();
	private Telefone telefoneSelecionado = new Telefone();
	private Emails email = new Emails();
	private Emails emailSelecionado = new Emails();
	private List<Emails> emails = new ArrayList<>();
	
	public String carregaMascaraCnpjOuCpfPrimefaces(){
		int tipo = 0;
		if(empresa != null && empresa.getFisicaJuridica() != null){
			tipo = empresa.getFisicaJuridica().getCodPessoa();
		}
		return Utils.mascarPrimefacesCnpjOuCpf(tipo);
	}
	
	public void adcionaTelefone(){
		FacesContext context = FacesContext.getCurrentInstance();
		IValidacaoCadastro validacao = new ValidacaoCadastroTelefone();
		telefone.setTipoTelefone(TipoTelefoneEnum.COMERCIAL);
		List<String> mensagens = validacao.validarCadastroComMensagem(telefone);
		if(mensagens.get(0).toUpperCase().equals("OK") && telefone != null){
			adcionaTelefoneList(this.telefone);
		}else{
			for(String mensagem : mensagens){
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Validação Telefone: " + mensagem, null));
				return;
			}
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
		FacesContext context = FacesContext.getCurrentInstance();
		IValidacaoCadastro validacao = new ValidacaoCadastroEmail();
		List<String> mensagens = validacao.validarCadastroComMensagem(email);
		if(mensagens.get(0).toUpperCase().equals("OK") && email != null){
			adcionaEmailList(this.email);
		}else{
			for(String mensagem : mensagens){
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Validação Email: " + mensagem, null));
				return;
			}
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
		FacesContext context = FacesContext.getCurrentInstance();
		if(validacoes(context)){
			completarDadosEmpresa();
			FornecedorDao fornDao = new FornecedorDao();
			if(fornDao.salvar(this.empresa)){
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Fornecedor cadastrado com suceso!", null));
			}else{
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro interno: erro interno contate a administração do sistema!", null));
			}
		}
	}

	private void completarDadosEmpresa() {
		this.endereco.setCidade(this.cidade);
		this.empresa.setEndereco(this.endereco);
		if (!this.telefones.isEmpty()) {
			this.empresa.setTelefones(this.telefones);
		}
		if (!this.emails.isEmpty()) {
			this.empresa.setEmails(this.emails);
		}
	}

	private boolean validarEndereco(FacesContext context) {
		IValidacaoCadastro validacao = new ValidacaoCadastroEndereco();
		this.endereco.setCidade(this.cidade);
		List<String> mensagens = validacao.validarCadastroComMensagem(endereco);
		if(mensagens.get(0).toUpperCase().equals("OK")){
			return true;
		}else{
			for(String mensagem : mensagens){
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Validação Endereço: " + mensagem, null));
			}
			return false;
		}
	}
	
	public boolean validacoes(FacesContext context){
		if(validarDadosPrincipais(context) & validarEndereco(context)){
			return true;
		}
		return false;
	}
	
	private boolean validarDadosPrincipais(FacesContext context) {
		List<String> mensagens = new ArrayList<>();
		IValidacaoCadastro validacao = new ValidacaoCadastroFornecedor();
		mensagens = validacao.validarCadastroComMensagem(empresa);
		if (mensagens.get(0).toUpperCase().equals("OK")) {
			return true;
		} else {
			for (String mensagem : mensagens) {
				context.addMessage(null, new FacesMessage(
						FacesMessage.SEVERITY_FATAL,
						"Validação Dados Principais: " + mensagem, null));
			}
			return false;
		}
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
}