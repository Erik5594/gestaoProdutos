package com.kilowats.servicos;

import java.io.Serializable;

import javax.inject.Inject;

import com.kilowats.annotations.ValidarTelefone;
import com.kilowats.entidades.TelefoneCliente;
import com.kilowats.interfaces.IValidacaoCadastro;

public class ServicosTelefone implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject @ValidarTelefone
	private IValidacaoCadastro validar;
	
	public boolean telefoneIsValido(TelefoneCliente telefone, String titulo, boolean mostrarMensagem){
		return validar.validarCadastroComMensagem(telefone, titulo, mostrarMensagem);
	}

}
