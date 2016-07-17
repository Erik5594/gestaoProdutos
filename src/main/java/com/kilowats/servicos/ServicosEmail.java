package com.kilowats.servicos;

import java.io.Serializable;

import javax.inject.Inject;

import com.kilowats.annotations.ValidarEmail;
import com.kilowats.entidades.Emails;
import com.kilowats.interfaces.IValidacaoCadastro;

public class ServicosEmail implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject @ValidarEmail
	private IValidacaoCadastro validador;
	
	public boolean emailIsValido(Emails email, String titulo){
		return validador.validarCadastroComMensagem(email, titulo);
	}

}
