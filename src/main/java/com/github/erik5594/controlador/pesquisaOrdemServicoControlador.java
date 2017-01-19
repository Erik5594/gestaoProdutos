package com.github.erik5594.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Data;

import com.github.erik5594.entidades.OrdemServico;
import com.github.erik5594.servicos.ServicosOrdemServico;

@Named
@ViewScoped
public @Data class pesquisaOrdemServicoControlador implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private OrdemServico ordemServicoSelecionada;
	private List<OrdemServico> ordemServicos;
	@Inject
	private ServicosOrdemServico servicosOrdemServico;
	
	public void init(){
		ordemServicos = new ArrayList<>();
		ordemServicos = servicosOrdemServico.listarTodasOrdemServico();
	}

}
