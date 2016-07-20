package com.kilowats.validadores;

import com.kilowats.annotations.ValidarEmail;
import com.kilowats.entidades.Emails;
import com.kilowats.interfaces.IValidacaoCadastro;
import com.kilowats.utils.Utils;
import com.kilowats.util.jsf.FacesUtils;

@ValidarEmail
public class ValidacaoCadastroEmail implements IValidacaoCadastro {

	@Override
	public boolean validarCadastro(Object obj) {
		Emails email = (Emails) obj;
		boolean retorno = true;
		if (email != null) {
			if (!isEmailValido(email.getEmailDestinatario())) {
				retorno = false;
			}
			if (!isNomeDestinatarioValido(email.getNomePessoaDestinatario())) {
				retorno =  false;
			}
		} else {
			retorno = false;
		}
		return retorno;
	}

	@Override
	public boolean validarCadastroComMensagem(Object obj, String titulo) {
		Emails email = (Emails) obj;
		boolean retorno = true;
		if (email != null) {
			if (!Utils.isNullOrEmpty(email.getEmailDestinatario())) {
				if (!email.getEmailDestinatario().contains("@")) {
					FacesUtils.sendMensagemError(titulo, "Email inválido!");
					retorno = false;
				}
			} else {
				FacesUtils.sendMensagemError(titulo, "Email está vazio!");
				retorno = false;
			}

			if (!Utils.isNullOrEmpty(email.getNomePessoaDestinatario())) {
				if (!email.getNomePessoaDestinatario().toLowerCase().equals("teste")) {
					if (email.getNomePessoaDestinatario().length() <= 1) {
						FacesUtils.sendMensagemError(titulo, "Nome do destinatário deve ser maior que 1 digito!");
						retorno = false;
					}
				} else {
					FacesUtils.sendMensagemError(titulo, "Nome do destinatário é inválido!");
					retorno = false;
				}
			} else {
				FacesUtils.sendMensagemError(titulo, "Nome do destinatário está vazio!");
				retorno = false;
			}
		} else {
			FacesUtils.sendMensagemError(titulo, "Não foram encontrados dados de Email!");
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
