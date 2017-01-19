package com.github.erik5594.validadores;

import com.github.erik5594.annotations.ValidarEndereco;
import com.github.erik5594.entidades.Endereco;
import com.github.erik5594.interfaces.IValidacaoCadastro;
import com.github.erik5594.util.Utils;
import com.github.erik5594.util.jsf.FacesUtils;

@ValidarEndereco
public class ValidacaoCadastroEndereco implements IValidacaoCadastro {

	@Override
	public boolean validarCadastroComMensagem(Object obj, String titulo, boolean mostrarMensagem) {
		Endereco endereco = (Endereco) obj;
		boolean retorno = true;
		if (endereco != null) {
			if (!isCepValido(endereco.getCep().getCep())) {
				if(mostrarMensagem){
					FacesUtils.sendMensagemError(titulo, "Cep inválido");
				}
				retorno = false;
			}
			if (!isStringEnderecoValida(endereco.getCep().getBairro()) && !isStringEnderecoValida(endereco.getBairro())) {
				if(mostrarMensagem){
					FacesUtils.sendMensagemError(titulo, "Bairro inválido");
				}
				retorno = false;
			}
			if (!isStringEnderecoValida(endereco.getComplemento())) {
				if(mostrarMensagem){
					FacesUtils.sendMensagemError(titulo, "Complemento inválido");
				}
				retorno = false;
			}
			if (!isStringEnderecoValida(endereco.getCep().getRua()) && !isStringEnderecoValida(endereco.getRua())) {
				if(mostrarMensagem){
					FacesUtils.sendMensagemError(titulo, "Rua inválida");
				}
				retorno = false;
			}
			if (endereco.getCep().getCidade() == null) {
				if(mostrarMensagem){
					FacesUtils.sendMensagemError(titulo, "Cidade não definida");
				}
				retorno = false;
			}
		} else {
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "Endereço não preenchido");
			}
			retorno = false;
		}
		return retorno;
	}

	public boolean isCepValido(Long cep) {
		if (cep == 0) {
			return false;
		}
		if (cep < 10000000) {
			return false;
		}
		if (cep > 99999999) {
			return false;
		}
		return true;
	}

	private boolean isStringEnderecoValida(String texto) {
		if (Utils.isNullOrEmpty(texto)) {
			return false;
		}
		if (texto.toLowerCase().equals("teste")) {
			return false;
		}
		if (texto.length() <= 2) {
			return false;
		}
		return true;
	}
}
