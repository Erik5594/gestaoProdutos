package com.github.erik5594.validadores;

import static com.github.erik5594.util.Utils.isNullOrEmpty;

import com.github.erik5594.annotations.ValidarDesconto;
import com.github.erik5594.entidades.Desconto;
import com.github.erik5594.interfaces.IValidacaoCadastro;
import com.github.erik5594.util.Utils;
import com.github.erik5594.util.jsf.FacesUtils;

@ValidarDesconto
public class ValidacaoCadastroTabelaDesconto implements IValidacaoCadastro {

	@Override
	public boolean validarCadastroComMensagem(Object obj, String titulo, boolean mostrarMensagem) {
		Desconto desconto = (Desconto) obj;
		boolean retorno = true;
		if(isNullOrEmpty(desconto)){
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "Desconto está vazio!");
			}
			retorno = false;
		}
		if(isNullOrEmpty(desconto.getNome())){
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "Nome do desconto é inválido!");
			}
			retorno = false;
		}
		if(Utils.isNull(desconto.getPercentualMinimoDesconto())){
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "Percentual mínimo inválido.");
			}
			retorno = false;
		}
		if(isNullOrEmpty(desconto.getPercentualMaximoDesconto())){
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "Percentual máximo inválido.");
			}
			retorno = false;
		}else if(desconto.getPercentualMaximoDesconto().compareTo(desconto.getPercentualMinimoDesconto()) < 0){
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "Percentual máximo menor que percentual mínimo.");
			}
			retorno = false;
		}
		
		if(isNullOrEmpty(desconto.getDataInicio())){
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "Data inicio inválida.");
			}
			retorno = false;
		}
		
		if(isNullOrEmpty(desconto.getDataFim())){
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "Data fim inválida.");
			}
			retorno = false;
		}else if(desconto.getDataFim().before(desconto.getDataInicio())){
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "Data fim menor que a data de inicio.");
			}
			retorno = false;
		}
		
		return retorno;
	}

}
