package com.kilowats.validadores;

import static com.kilowats.util.Utils.isNullOrEmpty;

import com.kilowats.annotations.ValidarValoresProduto;
import com.kilowats.entidades.ValoresProduto;
import com.kilowats.interfaces.IValidacaoCadastro;
import com.kilowats.util.jsf.FacesUtils;

@ValidarValoresProduto
public class ValidacaoValoresProduto implements IValidacaoCadastro {

	@Override
	public boolean validarCadastroComMensagem(Object obj, String titulo, boolean mostrarMensagem) {
		ValoresProduto valoresProduto = (ValoresProduto) obj;
		boolean retorno = true;
		if(isNullOrEmpty(valoresProduto)){
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "Valor não está preenchido!");
			}
			retorno = false;
		}
		if(isNullOrEmpty(valoresProduto.getProduto())){
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "Valores sem produto!");
			}
			retorno = false;
		}
		if(isNullOrEmpty(valoresProduto.getValorComercial())){
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "Valor comercial inválido");
			}
			retorno = false;
		}
		if(isNullOrEmpty(valoresProduto.getValorCusto())){
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "Valor de custo inválido");
			}
			retorno = false;
		}
		if(isNullOrEmpty(valoresProduto.getValorTributavel())){
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "Valor tributável inválido.");
			}
			retorno = false;
		}
		return retorno;
	}

}
