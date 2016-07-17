package com.kilowats.validadores;

import com.kilowats.annotations.ValidarEan;
import com.kilowats.entidades.Ean;
import com.kilowats.interfaces.IValidacaoCadastro;
import com.kilowats.utils.Utils;
import com.kilowats.utils.UtilsFaces;

@ValidarEan
public class ValidacaoCadastroEan implements IValidacaoCadastro {

	@Override
	public boolean validarCadastro(Object obj) {
		Ean ean = (Ean) obj;
		if(ean == null){
			return false;
		}
		if(Utils.isNullOrEmpty(ean.getCodBarras())){
			return false;
		}
		return true;
	}

	@Override
	public boolean validarCadastroComMensagem(Object obj, String titulo) {
		Ean ean = (Ean) obj;
		boolean retorno = true;
			if (ean == null) {
				UtilsFaces.sendMensagemError(titulo,
						"Código de Barras está vazio!");
				retorno = false;
			}
			if (Utils.isNullOrEmpty(ean.getCodBarras())) {
				UtilsFaces.sendMensagemError(titulo,
						"Código de Barras está vazio!");
				retorno = false;
			}
		return retorno;
	}

}
