package com.kilowats.controlador;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.kilowats.entidades.Produto;
import com.kilowats.servicos.ServicosProduto;

@Named
@ViewScoped
public class EntradaProdutoControlador implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Produto produto;
	@Inject
	private ServicosProduto servicosProduto;

}
