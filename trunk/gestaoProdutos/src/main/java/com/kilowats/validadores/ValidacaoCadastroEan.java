package com.kilowats.validadores;

import java.util.ArrayList;
import java.util.List;

import com.kilowats.entidades.Ean;
import com.kilowats.interfaces.IValidacaoCadastro;
import com.kilowats.utils.Utils;

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
	public List<String> validarCadastroComMensagem(Object obj) {
		List<String> mensagens = new ArrayList<>();
		Ean ean = (Ean) obj;
		if(ean == null){
			mensagens.add("Código de Barras está vazio!");
		}
		if(Utils.isNullOrEmpty(ean.getCodBarras())){
			mensagens.add("Código de Barras está vazio!");
		}
		
		if(mensagens.isEmpty()){
			mensagens.add("OK");
		}
		return mensagens;
	}

}
