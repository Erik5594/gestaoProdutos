package com.kilowats.validadores;

import org.apache.commons.lang3.StringUtils;

import com.kilowats.entidades.Endereco;
import com.kilowats.interfaces.IValidacaoCadastro;

public class ValidacaoCadastroEndereco implements IValidacaoCadastro {

	@Override
	public boolean validarCadastro(Object obj) {
		Endereco endereco = (Endereco) obj;
		
		if(endereco != null){
			if(!isCepValido(endereco.getCep())){
				return false;
			}
			if(!isStringEnderecoValida(endereco.getBairro())){
				return false;
			}
			if(!isStringEnderecoValida(endereco.getComplemento())){
				return false;
			}
			if(!isStringEnderecoValida(endereco.getRua())){
				return false;
			}
			if(endereco.getCidade() == null){
				return false;
			}
		}else{
			return false;
		}
		
		return true;
	}


	@Override
	public String validarCadastroComMensagem(Object obj) {
		return "OK";
	}
	
	public boolean isCepValido(int cep){
		if(cep == 0){
			return false;
		}
		if(cep < 10000000){
			return false;
		}
		if(cep > 99999999){
			return false;
		}
		return true;
	}

	private boolean isStringEnderecoValida(String texto) {
		if(StringUtils.isBlank(texto)){
			return false;
		}
		if(texto.toLowerCase().equals("teste")){
			return false;
		}
		if(texto.length() <= 2){
			return false;
		}
		return true;
	}
}
