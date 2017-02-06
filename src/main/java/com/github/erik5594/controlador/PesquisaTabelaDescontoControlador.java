package com.github.erik5594.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Data;

import com.github.erik5594.entidades.Desconto;
import com.github.erik5594.servicos.ServicosDesconto;

@Named
@ViewScoped
public @Data class PesquisaTabelaDescontoControlador implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Desconto DescontoSelecionado;
	private List<Desconto> descontos;
	@Inject
	private ServicosDesconto servicosDesconto;
	
	public void init(){
		descontos = new ArrayList<>();
		descontos = servicosDesconto.listarTodosDescontos();
	}

}
