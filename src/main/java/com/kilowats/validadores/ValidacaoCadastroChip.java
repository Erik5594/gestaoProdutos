package com.kilowats.validadores;

import com.kilowats.annotations.ValidarChip;
import com.kilowats.entidades.Chip;
import com.kilowats.interfaces.IValidacaoCadastro;
import com.kilowats.utils.Utils;
import com.kilowats.utils.UtilsFaces;

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
				UtilsFaces.sendMensagemError(titulo, "Chip não informado!");
				retorno = false;
			}
			if (Utils.isNullOrEmpty(chip.getImei())) {
				UtilsFaces.sendMensagemError(titulo, "Imei chip inválido!");
				retorno = false;
			}
			if (chip.getDdd() == 0) {
				UtilsFaces.sendMensagemError(titulo, "DDD inválido!");
				retorno = false;
			}
			if (chip.getNumero() < 9999999) {
				UtilsFaces.sendMensagemError(titulo, "Número do chip inválido");
				retorno = false;
			}
		return retorno;
	}

}
