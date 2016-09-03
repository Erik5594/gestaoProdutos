package com.kilowats.validadores;

import com.kilowats.annotations.ValidarEan;
import com.kilowats.entidades.Ean;
import com.kilowats.interfaces.IValidacaoCadastro;
import com.kilowats.util.Utils;
import com.kilowats.util.jsf.FacesUtils;

@ValidarEan
public class ValidacaoCadastroEan implements IValidacaoCadastro {

	@Override
	public boolean validarCadastroComMensagem(Object obj, String titulo, boolean mostrarMensagem) {
		Ean ean = (Ean) obj;
		boolean retorno = true;
			if (ean == null) {
				if(mostrarMensagem){
					FacesUtils.sendMensagemError(titulo,"Código de Barras está vazio!");
				}
				retorno = false;
			}
			if (Utils.isNullOrEmpty(ean.getCodBarras())) {
				if(mostrarMensagem){
					FacesUtils.sendMensagemError(titulo, "Código de Barras está vazio!");
				}
				retorno = false;
			}
		return retorno;
	}

}
