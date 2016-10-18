package com.kilowats.controlador;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Data;

import com.kilowats.entidades.OrdemServico;
import com.kilowats.entidades.Produto;

@Named
@ViewScoped
public @Data class GerarOrdemServicoControlador implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Produto produtoLinhaEditavel;
	@Inject
	private OrdemServico ordemServico;
	private String codBarras;

}
