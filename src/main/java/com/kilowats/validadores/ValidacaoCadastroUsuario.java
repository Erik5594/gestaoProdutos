package com.kilowats.validadores;

import com.kilowats.annotations.ValidarUsuario;
import com.kilowats.interfaces.IValidacaoCadastro;

@ValidarUsuario
public class ValidacaoCadastroUsuario implements IValidacaoCadastro {

	@Override
	public boolean validarCadastroComMensagem(Object obj, String titulo, boolean mostrarMensagem) {
		boolean retorno = true;
		return retorno;
	}

}
