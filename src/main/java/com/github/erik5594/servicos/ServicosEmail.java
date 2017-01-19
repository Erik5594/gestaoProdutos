package com.github.erik5594.servicos;

import java.io.Serializable;

import javax.inject.Inject;

import com.github.erik5594.annotations.ValidarEmail;
import com.github.erik5594.entidades.Email;
import com.github.erik5594.interfaces.IValidacaoCadastro;

public class ServicosEmail implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject @ValidarEmail
	private IValidacaoCadastro validador;
	
	public boolean emailIsValido(Email email, String titulo, boolean mostrarMensagem){
		return validador.validarCadastroComMensagem(email, titulo, mostrarMensagem);
	}

}
