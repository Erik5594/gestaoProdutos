package com.github.erik5594.validadores;

import com.github.erik5594.annotations.ValidarChip;
import com.github.erik5594.entidades.Chip;
import com.github.erik5594.interfaces.IValidacaoCadastro;
import com.github.erik5594.util.Utils;
import com.github.erik5594.util.jsf.FacesUtils;

@ValidarChip
public class ValidacaoCadastroChip implements IValidacaoCadastro {

	@Override
	public boolean validarCadastroComMensagem(Object obj, String titulo, boolean mostrarMensagem) {
		Chip chip = (Chip) obj;
		boolean retorno = true;
			if (chip == null) {
				if(mostrarMensagem){
					FacesUtils.sendMensagemError(titulo, "Chip não informado!");
				}
				retorno = false;
			}
			if (Utils.isNullOrEmpty(chip.getImei())) {
				if(mostrarMensagem){
					FacesUtils.sendMensagemError(titulo, "Imei chip inválido!");
				}
				retorno = false;
			}
			if (chip.getDdd() == 0) {
				if(mostrarMensagem){
					FacesUtils.sendMensagemError(titulo, "DDD inválido!");
				}
				retorno = false;
			}
			if (chip.getNumero() < 9999999) {
				if(mostrarMensagem){
					FacesUtils.sendMensagemError(titulo, "Número do chip inválido");
				}
				retorno = false;
			}
		return retorno;
	}

}
