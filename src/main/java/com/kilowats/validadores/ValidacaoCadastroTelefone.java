package com.kilowats.validadores;

import com.kilowats.annotations.ValidarTelefone;
import com.kilowats.entidades.Telefone;
import com.kilowats.interfaces.IValidacaoCadastro;
import com.kilowats.utils.UtilsFaces;

@ValidarTelefone
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
	public boolean validarCadastroComMensagem(Object obj, String titulo) {
		Telefone telefone = (Telefone) obj;
		boolean retorno = true;
		if(telefone != null){
			if(!isDddValido(telefone.getDdd())){
				UtilsFaces.sendMensagemError(titulo, "DDD inválido.");
				retorno = false;
			}
			if(!isNumeroValido(telefone.getNumero())){
				UtilsFaces.sendMensagemError(titulo, "Numero inválido.");
				retorno = false;
			}
			
			if(telefone.getTipoTelefone() == null){
				UtilsFaces.sendMensagemError(titulo, "Tipo de telefone inválido.");
				retorno = false;
			}
		}else{
			UtilsFaces.sendMensagemError(titulo, "Não foram encontrados dados de telefone");
			retorno = false;
		}
		return retorno;
	}

}
