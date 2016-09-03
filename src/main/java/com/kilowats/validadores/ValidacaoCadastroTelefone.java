package com.kilowats.validadores;

import com.kilowats.annotations.ValidarTelefone;
import com.kilowats.entidades.Telefone;
import com.kilowats.interfaces.IValidacaoCadastro;
import com.kilowats.util.jsf.FacesUtils;

@ValidarTelefone
public class ValidacaoCadastroTelefone implements IValidacaoCadastro {

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
	public boolean validarCadastroComMensagem(Object obj, String titulo, boolean mostrarMensagem) {
		Telefone telefone = (Telefone) obj;
		boolean retorno = true;
		if(telefone != null){
			if(!isDddValido(telefone.getDdd())){
				if(mostrarMensagem){
					FacesUtils.sendMensagemError(titulo, "DDD inválido.DDD["+telefone.getDdd()+"], Número["+telefone.getNumero()+"]");
				}
				retorno = false;
			}
			if(!isNumeroValido(telefone.getNumero())){
				if(mostrarMensagem){
					FacesUtils.sendMensagemError(titulo, "Numero inválido.DDD["+telefone.getDdd()+"], Número["+telefone.getNumero()+"]");
				}
				retorno = false;
			}
			
			if(telefone.getTipoTelefone() == null){
				if(mostrarMensagem){
					FacesUtils.sendMensagemError(titulo, "Tipo de telefone inválido.DDD["+telefone.getDdd()+"], Número["+telefone.getNumero()+"]");
				}
				retorno = false;
			}
		}else{
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "Não foram encontrados dados de telefone");
			}
			retorno = false;
		}
		return retorno;
	}

}
