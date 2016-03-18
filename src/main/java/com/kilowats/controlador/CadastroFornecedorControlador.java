package com.kilowats.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.kilowats.entidades.Cidade;
import com.kilowats.entidades.Empresa;
import com.kilowats.entidades.Endereco;
import com.kilowats.entidades.Telefone;
import com.kilowats.enuns.TipoTelefoneEnum;
import com.kilowats.interfaces.IValidacaoCadastro;
import com.kilowats.utils.Utils;
import com.kilowats.validadores.ValidacaoCadastroEndereco;
import com.kilowats.validadores.ValidacaoCadastroFornecedor;

@ManagedBean
@ViewScoped
public class CadastroFornecedorControlador implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Empresa empresa = new Empresa();
	private Endereco endereco = new Endereco();
	private Telefone telefone = new Telefone();
	private Cidade cidade = new Cidade();
	
	public String carregaMascaraCnpjOuCpfPrimefaces(){
		int tipo = 0;
		if(empresa != null && empresa.getFisicaJuridica() != null){
			tipo = empresa.getFisicaJuridica();
		}
		return Utils.mascarPrimefacesCnpjOuCpf(tipo);
	}
	
	public void salvar(){
		FacesContext context = FacesContext.getCurrentInstance();
		IValidacaoCadastro validacao = new ValidacaoCadastroEndereco();
		this.endereco.setCidade(this.cidade);
		if(validacao.validarCadastro(endereco)){
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastro Fornecedor Realizado com sucesso!", null));
		}else{
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Não foi possível realizar o cadastro do Fornecedor!", null));
		}
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
	
	

}
