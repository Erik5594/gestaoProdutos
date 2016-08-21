package com.kilowats.validadores;

import com.kilowats.annotations.ValidarUsuario;
import com.kilowats.entidades.Usuario;
import com.kilowats.interfaces.IValidacaoCadastro;
import com.kilowats.util.Utils;
import com.kilowats.util.jsf.FacesUtils;

@ValidarUsuario
public class ValidacaoCadastroUsuario implements IValidacaoCadastro {

	@Override
	public boolean validarCadastro(Object obj) {
		Usuario usuario = (Usuario) obj;
		boolean retorno = true;
		if (usuario != null) {
			if (Utils.isNullOrEmpty(usuario.getNome())) {
				retorno = false;
			}

			if (Utils.isNullOrEmpty(usuario.getSenha())) {
				retorno = false;
			}

			if (!Utils.isNullOrEmpty(usuario.getEmail())) {
				if (!usuario.getEmail().contains("@")) {
					retorno = false;
				}
			} else {
				retorno = false;
			}
		}
		return retorno;
	}

	@Override
	public boolean validarCadastroComMensagem(Object obj, String titulo) {
		Usuario usuario = (Usuario) obj;
		boolean retorno = true;
		if (usuario != null) {
			if (Utils.isNullOrEmpty(usuario.getNome())) {
				FacesUtils.sendMensagemError(titulo, "Nome em branco!");
				retorno = false;
			}

			if (Utils.isNullOrEmpty(usuario.getSenha())) {
				FacesUtils.sendMensagemError(titulo, "Senha inválida!");
				retorno = false;
			}

			if (!Utils.isNullOrEmpty(usuario.getEmail())) {
				if (!usuario.getEmail().contains("@")) {
					FacesUtils.sendMensagemError(titulo, "Email inválido!");
					retorno = false;
				}
			} else {
				FacesUtils.sendMensagemError(titulo, "Email está vazio!");
				retorno = false;
			}
		}
		return retorno;
	}

}
