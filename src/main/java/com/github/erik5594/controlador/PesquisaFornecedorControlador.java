package com.github.erik5594.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Data;

import com.github.erik5594.entidades.Fornecedor;
import com.github.erik5594.servicos.ServicosFornecedor;

@Named
@ViewScoped
public @Data class PesquisaFornecedorControlador implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Fornecedor fornecedoreselecionado;
	private List<Fornecedor> fornecedores;
	@Inject
	private ServicosFornecedor servicosFornecedor;
	
	public void init(){
		System.out.println("Iniciando pesquisa...");
		fornecedores = new ArrayList<>();
		fornecedores = servicosFornecedor.listarTodosFornecedores();
	}

}
