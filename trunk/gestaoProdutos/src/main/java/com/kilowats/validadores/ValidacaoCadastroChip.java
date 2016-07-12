package com.kilowats.validadores;

import java.util.ArrayList;
import java.util.List;

import com.kilowats.entidades.Chip;
import com.kilowats.interfaces.IValidacaoCadastro;
import com.kilowats.utils.Utils;

public class ValidacaoCadastroChip implements IValidacaoCadastro{

	@Override
	public boolean validarCadastro(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> validarCadastroComMensagem(Object obj) {
		List<String> mensagens = new ArrayList<>();
		Chip chip = (Chip) obj;
		if(chip == null){
			mensagens.add("Chip não informado!");
		}
		if(Utils.isNullOrEmpty(chip.getImei())){
			mensagens.add("Imei chip inválido!");
		}
		if(chip.getDdd() == 0){
			mensagens.add("DDD inválido!");
		}
		if(chip.getNumero() < 9999999){
			mensagens.add("Número do chip inválido");
		}
		if(mensagens.isEmpty()){
			mensagens.add("OK");
		}
		return mensagens;
	}

}
