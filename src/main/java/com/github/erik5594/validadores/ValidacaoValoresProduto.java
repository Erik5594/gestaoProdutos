package com.github.erik5594.validadores;

import static com.github.erik5594.util.Utils.isNullOrEmpty;

import com.github.erik5594.annotations.ValidarValoresProduto;
import com.github.erik5594.entidades.ValoresProduto;
import com.github.erik5594.interfaces.IValidacaoCadastro;
import com.github.erik5594.util.jsf.FacesUtils;

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
		/*if(isNullOrEmpty(valoresProduto.getValorTributavel())){
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "Valor tributável inválido.");
			}
			retorno = false;
		}*/
		return retorno;
	}

}
