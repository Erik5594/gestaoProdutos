package com.kilowats.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.kilowats.entidades.Empresa;
import com.kilowats.entidades.Telefone;
import com.kilowats.enuns.TipoTelefoneEnum;

@ManagedBean
@ViewScoped
public class CadastroFornecedorControlador implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Empresa fornecedor = new Empresa();
	private Telefone telefone = new Telefone();
	private List<Telefone> telefones = new ArrayList<>();

	public String carregarMascaraCgcCpf(){
		if(fornecedor.getFisicaJuridica() != null && fornecedor.getFisicaJuridica().equals("0")){
			return "999.999.999-99";
		}
		return "99.999.999/9999-99";
		
	}
	
	public void adicionarTelefone(){
		if(telefones == null){
			telefones = new ArrayList<>();
		}
		if(telefone != null){
			telefone.setTipoTelefone(TipoTelefoneEnum.COMERCIAL);
			telefones.add(telefone);
		}
	}
	
	public void salvar(){
		FacesContext contexto = FacesContext.getCurrentInstance();
		FacesMessage msg;
		if(fornecedor != null){
			if(fornecedor.getNome() != null && !"".equals(fornecedor.getNome())){
				if(fornecedor.getCgcCpf() != null && !"".equals(fornecedor.getCgcCpf())){
					msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cadastro Fornecedor: Cadastro fornecedor realizado com sucesso!", "");
				}else{
					msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Cadastro Fornecedor: CNPJ ou CPF do fornecedor não informado!", "");
				}
			}else{
				msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Cadastro Fornecedor: Nome do fornecedor não informado!", "");
			}
		}else{
			msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Cadastro Fornecedor: Erro interno contate a administração", "");
		}
		System.out.println("Nome Fornecedor: "+ fornecedor.getNome());
		System.out.println("CPF/CNPJ Fornecedor: "+fornecedor.getCgcCpf());
		contexto.addMessage(null, msg);
	}
	
	public Empresa getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(Empresa fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}



	public List<Telefone> getTelefones() {
		return telefones;
	}



	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}
	

}
