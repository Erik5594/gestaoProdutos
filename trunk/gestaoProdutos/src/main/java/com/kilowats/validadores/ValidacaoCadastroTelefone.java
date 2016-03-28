package com.kilowats.validadores;

import java.util.ArrayList;
import java.util.List;

import com.kilowats.entidades.Telefone;
import com.kilowats.interfaces.IValidacaoCadastro;

public class ValidacaoCadastroTelefone implements IValidacaoCadastro {

	@Override
	public boolean validarCadastro(Object obj) {
		Telefone telefone = (Telefone) obj;
		if(telefone != null){
			if(!isDddValido(telefone.getDdd())){
				return false;
			}
			if(!isNumeroValido(telefone.getNumero())){
				return false;
			}
			
			if(telefone.getTipoTelefone() == null){
				return false;
			}
		}else{
			return false;
		}
		return true;
	}

	private boolean isNumeroValido(String numero) {
		if(numero.equals("") || numero == null){
			return false;
		}
		if(numero.length() < 8){
			return false;
		}
		if(numero.length() > 9){
			return false;
		}
		if(numero.contains("[a-zA-Z]")){
			return false;
		}
		return true;
	}

	private boolean isDddValido(int ddd) {
		if(ddd < 10){
			return false;
		}
		if(ddd == 0){
			return false;
		}
		if(ddd > 99){
			return false;
		}
		return true;
	}

	@Override
	public List<String> validarCadastroComMensagem(Object obj) {
		Telefone telefone = (Telefone) obj;
		List<String> mensagens = new ArrayList<>();
		if(telefone != null){
			if(!isDddValido(telefone.getDdd())){
				mensagens.add("DDD inválido.");
			}
			if(!isNumeroValido(telefone.getNumero())){
				mensagens.add("Numero inválido.");
			}
			
			if(telefone.getTipoTelefone() == null){
				mensagens.add("Tipo de telefone inválido.");
			}
		}else{
			mensagens.add("Não foram encontrados dados de telefone");
		}
		if(mensagens.isEmpty()){
			mensagens.add("OK");
		}
		return mensagens;
	}

}
