package com.kilowats.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Data;

import com.kilowats.entidades.Produto;
import com.kilowats.servicos.ServicosProduto;

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
		System.out.println("Iniciando pesquisa...");
		produtos = new ArrayList<>();
		produtos = servicosProduto.listarTodosProdutos();
	}

}
