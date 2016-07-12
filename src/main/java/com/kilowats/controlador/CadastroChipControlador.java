package com.kilowats.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.kilowats.entidades.Chip;
import com.kilowats.interfaces.IValidacaoCadastro;
import com.kilowats.validadores.ValidacaoCadastroChip;

@ManagedBean
@ViewScoped
public class CadastroChipControlador implements Serializable{

	private static final long serialVersionUID = 1L;
	private int codInternoProduto = 0;
	private int codProduto = 0;
	private boolean mostrarConteudo = false;
	private Chip chip = new Chip();
	
	public void buscarProduto(){
		if(codInternoProduto > 0 && codProduto > 0){
			mostrarConteudo = true;
		}
	}
	
	public void salvar(){
		FacesContext context = FacesContext.getCurrentInstance();
		if(validacoes(context)){
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Chip cadastrado com suceso!", null));
		}
	}

	private boolean validacoes(FacesContext context) {
		IValidacaoCadastro validacao = new ValidacaoCadastroChip();
		List<String> mensagens = new ArrayList<>();
		mensagens = validacao.validarCadastroComMensagem(chip);
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

	public int getCodInternoProduto() {
		return codInternoProduto;
	}

	public void setCodInternoProduto(int codInternoProduto) {
		this.codInternoProduto = codInternoProduto;
	}

	public int getCodProduto() {
		return codProduto;
	}

	public void setCodProduto(int codProduto) {
		this.codProduto = codProduto;
	}

	public boolean isMostrarConteudo() {
		return mostrarConteudo;
	}

	public void setMostrarConteudo(boolean mostrarConteudo) {
		this.mostrarConteudo = mostrarConteudo;
	}
}
