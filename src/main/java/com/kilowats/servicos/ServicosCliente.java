package com.kilowats.servicos;

import java.io.Serializable;

import javax.inject.Inject;

import com.kilowats.annotations.ValidarCliente;
import com.kilowats.dao.ClienteDao;
import com.kilowats.entidades.Cliente;
import com.kilowats.interfaces.IValidacaoCadastro;

public class ServicosCliente implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ClienteDao clienteDao;
	@Inject @ValidarCliente
	private IValidacaoCadastro validar;

	public Cliente persistirCliente(Cliente cliente){
		return clienteDao.salvarOrUpdate(cliente);
	}
	
	public boolean validarCliente(Cliente cliente, String titulo, boolean mostrarMensagem){
		return validar.validarCadastroComMensagem(cliente, titulo, mostrarMensagem);
	}
}
