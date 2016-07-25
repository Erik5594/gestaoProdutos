package com.kilowats.validadores;

import com.kilowats.annotations.ValidarChip;
import com.kilowats.entidades.Chip;
import com.kilowats.interfaces.IValidacaoCadastro;
import com.kilowats.util.Utils;
import com.kilowats.util.jsf.FacesUtils;

@ValidarChip
public class ValidacaoCadastroChip implements IValidacaoCadastro {

	@Override
	public boolean validarCadastro(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean validarCadastroComMensagem(Object obj, String titulo) {
		Chip chip = (Chip) obj;
		boolean retorno = true;
			if (chip == null) {
				FacesUtils.sendMensagemError(titulo, "Chip não informado!");
				retorno = false;
			}
			if (Utils.isNullOrEmpty(chip.getImei())) {
				FacesUtils.sendMensagemError(titulo, "Imei chip inválido!");
				retorno = false;
			}
			if (chip.getDdd() == 0) {
				FacesUtils.sendMensagemError(titulo, "DDD inválido!");
				retorno = false;
			}
			if (chip.getNumero() < 9999999) {
				FacesUtils.sendMensagemError(titulo, "Número do chip inválido");
				retorno = false;
			}
		return retorno;
	}

}
