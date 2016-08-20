package com.kilowats.servicos;

import java.io.Serializable;

import javax.inject.Inject;

import com.kilowats.annotations.CadastrarCliente;
import com.kilowats.annotations.ValidarCliente;
import com.kilowats.entidades.Cliente;
import com.kilowats.interfaces.IPersistirBancoDados;
import com.kilowats.interfaces.IValidacaoCadastro;

public class ServicosCliente implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject @CadastrarCliente
	private IPersistirBancoDados persistir;
	@Inject @ValidarCliente
	private IValidacaoCadastro validar;

	public boolean persistirCliente(Cliente cliente){
		return false;
	}
	
	public boolean validarCliente(Cliente cliente, String titulo){
		return validar.validarCadastroComMensagem(cliente, titulo);
	}
}
