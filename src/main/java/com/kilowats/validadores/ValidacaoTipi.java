package com.kilowats.validadores;

import static com.kilowats.util.Utils.isNullOrEmpty;

import com.kilowats.annotations.ValidarValoresProduto;
import com.kilowats.entidades.Tipi;
import com.kilowats.interfaces.IValidacaoCadastro;
import com.kilowats.util.jsf.FacesUtils;

@ValidarValoresProduto
public class ValidacaoTipi implements IValidacaoCadastro {

	@Override
	public boolean validarCadastroComMensagem(Object obj, String titulo, boolean mostrarMensagem) {
		Tipi tipi = (Tipi) obj;
		boolean retorno = true;
		if(isNullOrEmpty(tipi)){
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "Tipi não preenchido!");
			}
			retorno = false;
		}
		if(isNullOrEmpty(tipi.getProduto())){
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "TIPI sem produto!");
			}
			retorno = false;
		}
		if(isNullOrEmpty(tipi.getCodGenero())){
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "TIPI - Código de genero não informado!");
			}
			retorno = false;
		}
		if(isNullOrEmpty(tipi.getNcm())){
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "TIPI - Código do NCM não informado!");
			}
			retorno = false;
		}
		return retorno;
	}

}
