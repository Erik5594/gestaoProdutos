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
import com.kilowats.servicos.ServicosChip;
import com.kilowats.validadores.ValidacaoCadastroChip;

@ManagedBean
@ViewScoped
public class CadastroChipControlador implements Serializable{

	private static final long serialVersionUID = 1L;
	private int codInternoProduto = 0;
	private int codProduto = 0;
	private boolean mostrarConteudo = false;
	private Chip chip = new Chip();
	private List<Chip> chips = new ArrayList<>();
	private String ddd;
	private String numero;
	
	
	private void inicializarVariaveis(){
		chip = new Chip();
		ddd = new String();
		numero = new String();
	}
	public void buscarProduto(){
		//PRECISA SER IMPLEMENTADO A REGRA
		if(codInternoProduto > 0 || codProduto > 0){
			mostrarConteudo = true;
		}
	}
	
	public void salvar() {
		FacesContext context = FacesContext.getCurrentInstance();
		persistirBanco(context);
	}

	private void adicionarDddNumeroNoChip() throws NumberFormatException {
		chip.setDdd(Integer.parseInt(ddd.replaceAll("\\D", "")));
		chip.setNumero(Long.parseLong(numero.replaceAll("\\D", "")));
	}

	private void persistirBanco(FacesContext context) {
		for (Chip chip1 : chips) {
			if (ServicosChip.persistirChip(chip1)) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Chip ["+chip1.getImei()+"] cadastrado com suceso!", null));
			} else {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro interno: erro interno contate a administração do sistema!",null));
			}
		}
	}
	
	public void adicionarChip() {
		FacesContext context = FacesContext.getCurrentInstance();
		adicionarDddNumeroNoChip();
		if (validacoes(context)) {
			if (chips == null && chips.isEmpty()) {
				chips = new ArrayList<>();
			}
			chips.add(chip);
		}
		inicializarVariaveis();
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

	public Chip getChip() {
		return chip;
	}

	public void setChip(Chip chip) {
		this.chip = chip;
	}

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public List<Chip> getChips() {
		return chips;
	}

	public void setChips(List<Chip> chips) {
		this.chips = chips;
	}
	
}
