package com.kilowats.validadores;

import com.kilowats.annotations.ValidarEmail;
import com.kilowats.entidades.EmailCliente;
import com.kilowats.interfaces.IValidacaoCadastro;
import com.kilowats.util.Utils;
import com.kilowats.util.jsf.FacesUtils;

@ValidarEmail
public class ValidacaoCadastroEmail implements IValidacaoCadastro {

	@Override
	public boolean validarCadastroComMensagem(Object obj, String titulo, boolean mostrarMensagem) {
		EmailCliente email = (EmailCliente) obj;
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
