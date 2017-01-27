package com.github.erik5594.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Data;

import com.github.erik5594.entidades.Cliente;
import com.github.erik5594.servicos.ServicosCliente;

@Named
@ViewScoped
public @Data class PesquisaClienteControlador implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Cliente clienteSelecionado;
	private List<Cliente> clientes;
	@Inject
	private ServicosCliente servicosCliente;
	
	public void init(){
		clientes = new ArrayList<>();
		clientes = servicosCliente.listarTodosClientes();
	}

}
