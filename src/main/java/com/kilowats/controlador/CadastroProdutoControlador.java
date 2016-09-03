package com.kilowats.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.kilowats.entidades.Ean;
import com.kilowats.entidades.Produto;
import com.kilowats.enuns.TipoProdutoUnidadeEnum;
import com.kilowats.servicos.ServicosEan;
import com.kilowats.servicos.ServicosProduto;
import com.kilowats.util.jsf.FacesUtils;

@Named
@ViewScoped
public class CadastroProdutoControlador implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Produto produto;
	@Inject
	private Ean ean;
	@Inject
	private ServicosEan servicosEan;
	@Inject
	private ServicosProduto servicosProduto;
	
	private final String TITULO = "Cadastro produto: ";
	private final String ERRO_INTERNO = "Erro interno: erro interno contate a administração do sistema!";
	
	private List<Ean> eans = new ArrayList<>();
	
	private void iniciarVariaveis(){
		this.produto = new Produto();
		this.ean = new Ean();
		this.eans = new ArrayList<>();
	}
	
	public void adicionarEan(){
		if(servicosEan.eanIsValido(ean, TITULO, true)){
			eans.add(ean);
		}
		ean = new Ean();
	}
	
	public void salvar() {
		if (validacoes()) {
			completarDadosProduto();
			if (servicosProduto.persistirProduto(this.produto)) {
				FacesUtils.sendMensagemOk(TITULO, "Produto cadastrado com sucesso!");
			} else {
				FacesUtils.sendMensagemError(TITULO, ERRO_INTERNO);
			}
			iniciarVariaveis();
		}
	}
	
	private void completarDadosProduto() {
		if(this.eans != null && !this.eans.isEmpty()){
			this.produto.setEans(this.eans);
		}
	}

	private boolean validacoes(){
		return servicosProduto.produtoIsValido(this.produto, TITULO, true);
	}

	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public Ean getEan() {
		return ean;
	}
	public void setEan(Ean ean) {
		this.ean = ean;
	}
	public List<Ean> getEans() {
		return eans;
	}
	public void setEans(List<Ean> eans) {
		this.eans = eans;
	}
	public TipoProdutoUnidadeEnum[] getTipoUnidadeProduto() {
		return TipoProdutoUnidadeEnum.values();
	}
	
}
