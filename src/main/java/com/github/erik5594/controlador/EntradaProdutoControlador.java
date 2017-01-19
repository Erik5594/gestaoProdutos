package com.github.erik5594.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import lombok.Data;

import com.github.erik5594.entidades.Produto;
import com.github.erik5594.servicos.ServicosEan;
import com.github.erik5594.servicos.ServicosProduto;
import com.github.erik5594.util.Utils;
import com.github.erik5594.util.jsf.FacesUtils;

@Named
@ViewScoped
public @Data class EntradaProdutoControlador implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Produto produto;
	private Produto produtoSelecionado;
	@Inject
	private ServicosProduto servicosProduto;
	@Inject
	private ServicosEan servicosEan;
	private String chavePesquisa;
	
	private List<Produto> produtos;
	
	private String TITULO = "Movimentação Entrada Produto: ";
	private final String ERRO_INTERNO = "Erro interno: erro interno contate a administração do sistema!";
	
	public void buscarProduto(){
		if(Utils.isNotNullOrEmpty(chavePesquisa)){
			if(chavePesquisa.length() > 10){
				buscarProdutoByCodBarras();
			}else{
				buscarProdutoByCodProduto();
			}
		}
		if(Utils.isNull(produto) || Utils.isNullOrEmpty(produto.getId())){
			FacesUtils.sendMensagemAviso(TITULO, "Nenhum produto encontrado!");
		}
	}
	
	private void buscarProdutoByCodProduto(){
		try{
			produto = servicosProduto.pesquisaProdutoByCodProduto(chavePesquisa);
		}catch(NoResultException ex){
			FacesUtils.sendMensagemAviso(TITULO, "Não foi encontrado produto!");
		}
	}
	
	private void buscarProdutoByCodBarras(){
		try{
			produto = servicosEan.pesquisarEanByCodBarras(chavePesquisa).getProduto();
		}catch(NoResultException ex){
			FacesUtils.sendMensagemAviso(TITULO, "Não foi encontrado produto!");
		}
	}
	
	public void alterarProdutos(){
		servicosProduto.persistirListaProduto(produtos);
		inicializarVariaveis();
	}

	private void inicializarVariaveis() {
		inicializarParaNovaBusca();
		produtos = new ArrayList<>();
	}

	private void inicializarParaNovaBusca() {
		produto = new Produto();
		chavePesquisa = "";
	}
	
	public void adicionarProduto(){
		if(servicosProduto.produtoIsValido(produto, TITULO, true)){
			adicionarProdutoNaLista(this.produto);
		}
		inicializarParaNovaBusca();
	}
	
	private void adicionarProdutoNaLista(Produto produto){
		if(Utils.isNullOrEmpty(produtos)){
			produtos = new ArrayList<>();
		}else{
			if(produtos.contains(produto)){
				FacesUtils.sendMensagemError(TITULO, "Validação Produto: Produto já adicionado!");
				return;
			}
		}
		produtos.add(produto);
	}

}
