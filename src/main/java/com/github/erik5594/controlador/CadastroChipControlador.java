package com.github.erik5594.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.github.erik5594.annotations.ProdutoChip;
import com.github.erik5594.entidades.Chip;
import com.github.erik5594.servicos.ServicosChip;
import com.github.erik5594.util.Utils;
import com.github.erik5594.util.jsf.FacesUtils;

@Named
@ViewScoped
public class CadastroChipControlador implements Serializable{

	private static final long serialVersionUID = 1L;
	private int codInternoProduto = 0;
	private int codProduto = 0;
	private boolean mostrarConteudo = false;
	@Inject @ProdutoChip
	private Chip chip = new Chip();
	@Inject
	private ServicosChip servicos;
	private List<Chip> chips = new ArrayList<>();
	private String ddd;
	private String numero;
	private final String TITULO = "Cadastro Chip: ";
	private final String ERRO_INTERNO = "Erro interno: erro interno contate a administração do sistema!";
	
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
			chip1 = servicos.persistirChip(chip1);
			if (Utils.isNotNullOrEmpty(chip1) && chip1.getIdChip() > 0L) {
				FacesUtils.sendMensagemOk(TITULO, "Chip ["+chip1.getImei()+"] cadastrado com sucesso!");
			} else {
				FacesUtils.sendMensagemError(TITULO,ERRO_INTERNO+" ["+chip1.getImei()+"]");
			}
		}
	}
	
	public void adicionarChip() {
		adicionarDddNumeroNoChip();
		if (validacoes()) {
			if (chips == null && chips.isEmpty()) {
				chips = new ArrayList<>();
			}
			chips.add(chip);
		}
		inicializarVariaveis();
	}

	private boolean validacoes() {
		return servicos.chipIsValido(chip, TITULO, true);
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
