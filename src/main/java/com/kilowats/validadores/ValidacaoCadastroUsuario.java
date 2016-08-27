package com.kilowats.validadores;

import com.kilowats.annotations.ValidarUsuario;
import com.kilowats.entidades.Usuario;
import com.kilowats.interfaces.IValidacaoCadastro;

@ValidarUsuario
public class ValidacaoCadastroUsuario implements IValidacaoCadastro {

	@Override
	public boolean validarCadastro(Object obj) {
		Usuario usuario = (Usuario) obj;
		boolean retorno = true;
		if (usuario != null) {
			if (!usuario.getEmail().contains("@")) {
				retorno = false;
			}
		}
		return retorno;
	}

	@Override
	public boolean validarCadastroComMensagem(Object obj, String titulo) {
		Usuario usuario = (Usuario) obj;
		boolean retorno = true;
		return retorno;
	}

}
