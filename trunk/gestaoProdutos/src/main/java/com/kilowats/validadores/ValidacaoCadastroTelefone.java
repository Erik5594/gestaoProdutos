package com.kilowats.validadores;

import java.util.ArrayList;
import java.util.List;

import com.kilowats.entidades.Telefone;
import com.kilowats.interfaces.IValidacaoCadastro;
import com.kilowats.utils.Utils;

public class ValidacaoCadastroTelefone implements IValidacaoCadastro {

	@Override
	public boolean validarCadastro(Object obj) {
		Telefone telefone = (Telefone) obj;
		if (Utils.isNullOrEmpty(telefone.getNumero())) {
			return false;
		}
		if(telefone.getNumero().length() < 8){
			return false;
		}
		try{
			Integer.parseInt(telefone.getNumero());
		}catch(Exception ex){
			return false;
		}
		if(telefone.getDdd() < 2){
			return false;
		}
		return true;
	}

	@Override
	public List<String> validarCadastroComMensagem(Object obj) {
		Telefone telefone = (Telefone) obj;
		List<String> mensagens = new ArrayList<>();
		if (Utils.isNullOrEmpty(telefone.getNumero())) {
			mensagens.add("Numero não está preenchido");
		}
		if(telefone.getNumero().length() < 8){
			mensagens.add("Numero inválido");
		}
		try{
			Integer.parseInt(telefone.getNumero());
		}catch(Exception ex){
			mensagens.add("Numero com caracter(es) inválido(s)");
		}
		if(telefone.getDdd() < 2){
			mensagens.add("DDD inválido");
		}
		if(mensagens.isEmpty()){
			mensagens.add("OK");
		}
		return mensagens;
	}

}
