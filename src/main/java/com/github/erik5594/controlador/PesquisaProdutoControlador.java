package com.github.erik5594.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Data;

import com.github.erik5594.entidades.Produto;
import com.github.erik5594.servicos.ServicosProduto;

@Named
@ViewScoped
public @Data class PesquisaProdutoControlador implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Produto produtoSelecionado;
	private List<Produto> produtos;
	@Inject
	private ServicosProduto servicosProduto;
	
	public void init(){
		produtos = new ArrayList<>();
		produtos = servicosProduto.listarTodosProdutos();
	}

}
