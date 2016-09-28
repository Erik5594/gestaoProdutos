package com.kilowats.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Data;

import com.kilowats.entidades.Cliente;
import com.kilowats.servicos.ServicosCliente;

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
		System.out.println("Iniciando pesquisa...");
		clientes = new ArrayList<>();
		clientes = servicosCliente.listarTodosClientes();
	}

}
