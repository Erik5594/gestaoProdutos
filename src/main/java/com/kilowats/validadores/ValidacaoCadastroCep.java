package com.kilowats.validadores;

import com.kilowats.annotations.ValidarCep;
import com.kilowats.entidades.Cep;
import com.kilowats.interfaces.IValidacaoCadastro;
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
