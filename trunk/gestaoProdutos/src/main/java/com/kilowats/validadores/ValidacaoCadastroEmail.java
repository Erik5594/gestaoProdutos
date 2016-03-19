package com.kilowats.validadores;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.kilowats.entidades.Emails;
import com.kilowats.interfaces.IValidacaoCadastro;

public class ValidacaoCadastroEmail implements IValidacaoCadastro {

	@Override
	public boolean validarCadastro(Object obj) {
		Emails email = (Emails) obj;
		if (email != null) {
			if (!isEmailValido(email.getEmailDestinatario())) {
				return false;
			}
			if (!isNomeDestinatarioValido(email.getNomePessoaDestinatario())) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	@Override
	public List<String> validarCadastroComMensagem(Object obj) {
		Emails email = (Emails) obj;
		List<String> mensagens = new ArrayList<>();
		if (email != null) {
			if (!StringUtils.isBlank(email.getEmailDestinatario())) {
				if (!email.getEmailDestinatario().contains("@")) {
					mensagens.add("Email inválido!");
				}
			} else {
				mensagens.add("Email está vazio!");
			}

			if (!StringUtils.isBlank(email.getNomePessoaDestinatario())) {
				if (!email.getNomePessoaDestinatario().toLowerCase()
						.equals("teste")) {
					if (email.getNomePessoaDestinatario().length() <= 1) {
						mensagens
								.add("Nome do destinatário deve ser maior que 1 digito!");
					}
				} else {
					mensagens.add("Nome do destinatário é inválido!");
				}
			} else {
				mensagens.add("Nome do destinatário está vazio!");
			}
		} else {
			mensagens.add("Não foram encontrados dados de Email!");
		}
		if (mensagens.isEmpty()) {
			mensagens.add("OK");
		}
		return mensagens;
	}

	private boolean isEmailValido(String emailDestinatario) {
		if (!StringUtils.isBlank(emailDestinatario)) {
			if (emailDestinatario.contains("@")) {
				return true;
			}
		}
		return false;
	}

	public boolean isNomeDestinatarioValido(String nomeDestinatario) {
		if (!StringUtils.isBlank(nomeDestinatario)) {
			if (!nomeDestinatario.toLowerCase().equals("teste")) {
				if (nomeDestinatario.length() > 1) {
					return true;
				}
			}
		}
		return false;
	}

}
