package com.github.erik5594.validadores;

import com.github.erik5594.annotations.ValidarEan;
import com.github.erik5594.entidades.Ean;
import com.github.erik5594.interfaces.IValidacaoCadastro;
import com.github.erik5594.util.Utils;
import com.github.erik5594.util.jsf.FacesUtils;

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
			}else if(ean.getCodBarras().length() < 13){
				if(mostrarMensagem){
					FacesUtils.sendMensagemError(titulo, "Código de Barras inválido!");
				}
				retorno = false;
			}
		return retorno;
	}

}
