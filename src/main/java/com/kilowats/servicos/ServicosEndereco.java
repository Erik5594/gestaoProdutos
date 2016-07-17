package com.kilowats.servicos;

import java.io.Serializable;

import javax.inject.Inject;

import com.kilowats.annotations.ValidarEndereco;
import com.kilowats.entidades.Endereco;
import com.kilowats.interfaces.IValidacaoCadastro;

public class ServicosEndereco implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject @ValidarEndereco
	private IValidacaoCadastro validador;
	
	public boolean enderecoIsValido(Endereco endereco, String titulo){
		return validador.validarCadastroComMensagem(endereco, titulo);
	}

}
