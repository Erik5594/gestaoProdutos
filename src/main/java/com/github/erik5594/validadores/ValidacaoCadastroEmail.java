package com.github.erik5594.validadores;

import com.github.erik5594.annotations.ValidarEmail;
import com.github.erik5594.entidades.Email;
import com.github.erik5594.interfaces.IValidacaoCadastro;
import com.github.erik5594.util.Utils;
import com.github.erik5594.util.jsf.FacesUtils;

@ValidarEmail
public class ValidacaoCadastroEmail implements IValidacaoCadastro {

	@Override
	public boolean validarCadastroComMensagem(Object obj, String titulo, boolean mostrarMensagem) {
		Email email = (Email) obj;
		boolean retorno = true;
		if (email != null) {
			if (!Utils.isEmailValido(email.getEmailDestinatario())) {
				if(mostrarMensagem){
					FacesUtils.sendMensagemError(titulo, "Email inválido!");
				}
				retorno = false;
			}

			if (!Utils.isNomeValido(email.getNomePessoaDestinatario())) {
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

}
