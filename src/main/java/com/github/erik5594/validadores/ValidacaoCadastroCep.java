package com.github.erik5594.validadores;

import com.github.erik5594.annotations.ValidarCep;
import com.github.erik5594.entidades.Cep;
import com.github.erik5594.interfaces.IValidacaoCadastro;
import com.github.erik5594.util.jsf.FacesUtils;

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
}
