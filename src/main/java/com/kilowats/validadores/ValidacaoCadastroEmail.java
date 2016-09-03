package com.kilowats.validadores;

import com.kilowats.annotations.ValidarEmail;
import com.kilowats.entidades.Email;
import com.kilowats.interfaces.IValidacaoCadastro;
import com.kilowats.util.Utils;
import com.kilowats.util.jsf.FacesUtils;

@ValidarEmail
public class ValidacaoCadastroEmail implements IValidacaoCadastro {

	@Override
	public boolean validarCadastroComMensagem(Object obj, String titulo, boolean mostrarMensagem) {
		Email email = (Email) obj;
		boolean retorno = true;
		if (email != null) {
			if (!isEmailValido(email.getEmailDestinatario())) {
				if(mostrarMensagem){
					FacesUtils.sendMensagemError(titulo, "Email inválido!");
				}
				retorno = false;
			}

			if (!isNomeDestinatarioValido(email.getNomePessoaDestinatario())) {
				if(mostrarMensagem){
					FacesUtils.sendMensagemError(titulo, "Nome do destinatário inválido!");
				}
				retorno = false;
			}
		} else {
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "Não foram encontrados dados de Email!");
			}
			retorno = false;
		}
		return retorno;
	}

	private boolean isEmailValido(String emailDestinatario) {
		if (!Utils.isNullOrEmpty(emailDestinatario)) {
			if (emailDestinatario.contains("@")) {
				return true;
			}
		}
		return false;
	}

	public boolean isNomeDestinatarioValido(String nomeDestinatario) {
		if (!Utils.isNullOrEmpty(nomeDestinatario)) {
			if (!nomeDestinatario.toLowerCase().equals("teste")) {
				if (nomeDestinatario.length() > 1) {
					return true;
				}
			}
		}
		return false;
	}

}
