package com.github.erik5594.servicos;

import java.io.Serializable;

import javax.inject.Inject;

import com.github.erik5594.annotations.ValidarTelefone;
import com.github.erik5594.entidades.Telefone;
import com.github.erik5594.interfaces.IValidacaoCadastro;

public class ServicosTelefone implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject @ValidarTelefone
	private IValidacaoCadastro validar;
	
	public boolean telefoneIsValido(Telefone telefone, String titulo, boolean mostrarMensagem){
		return validar.validarCadastroComMensagem(telefone, titulo, mostrarMensagem);
	}

}
