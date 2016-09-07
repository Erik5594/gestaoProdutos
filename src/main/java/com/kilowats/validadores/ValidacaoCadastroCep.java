package com.kilowats.validadores;

import com.kilowats.annotations.ValidarCep;
import com.kilowats.entidades.Cep;
import com.kilowats.interfaces.IValidacaoCadastro;
import com.kilowats.util.Utils;
import com.kilowats.util.jsf.FacesUtils;

@ValidarCep
public class ValidacaoCadastroCep implements IValidacaoCadastro {

	@Override
	public boolean validarCadastroComMensagem(Object obj, String titulo, boolean mostrarMensagem) {
		Cep cep = (Cep) obj;
		boolean retorno = true;
		if (cep != null) {
			if(cep.getCep() != null && cep.getCep() < 1){
				if(mostrarMensagem){
					FacesUtils.sendMensagemError(titulo, "Cep inválido");
				}
				retorno = false;
			}
			if(!isStringEnderecoValida(cep.getBairro())){
				if(mostrarMensagem){
					FacesUtils.sendMensagemError(titulo, "Bairro inválido");
				}
				retorno = false;
			}
			if(!isStringEnderecoValida(cep.getRua())){
				if(mostrarMensagem){
					FacesUtils.sendMensagemError(titulo, "Logradouro inválido");
				}
				retorno = false;
			}
			if(cep.getCidade() == null){
				if(mostrarMensagem){
					FacesUtils.sendMensagemError(titulo, "Cep informado deve pertencer a uma cidade");
				}
				retorno = false;
			}
		}else{
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "Cep deve ser informado");
			}
			retorno = false;
		}
		return retorno;
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
