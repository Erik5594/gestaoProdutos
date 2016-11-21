package com.kilowats.validadores;

import static com.kilowats.util.Utils.isNullOrEmpty;

import com.kilowats.annotations.ValidarValoresProduto;
import com.kilowats.entidades.Icms;
import com.kilowats.interfaces.IValidacaoCadastro;
import com.kilowats.util.jsf.FacesUtils;

@ValidarValoresProduto
public class ValidacaoIcms implements IValidacaoCadastro {

	@Override
	public boolean validarCadastroComMensagem(Object obj, String titulo, boolean mostrarMensagem) {
		Icms icms = (Icms) obj;
		boolean retorno = true;
		if(isNullOrEmpty(icms)){
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "ICMS não está preenchido!");
			}
			retorno = false;
		}
		if(isNullOrEmpty(icms.getProduto())){
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "Valores sem produto!");
			}
			retorno = false;
		}
		if(isNullOrEmpty(icms.getOrigemTributavel())){
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "ICMS - Origem não preenchido!");
			}
			retorno = false;
		}
		if(isNullOrEmpty(icms.getSituacaoTributariaIcms())){
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "ICMS - Situação não preenchido!");
			}
			retorno = false;
		}
		if(isNullOrEmpty(icms.getTipoRegimeFiscal())){
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "ICMS - Regime fiscal não preenchido!");
			}
			retorno = false;
		}
		if(isNullOrEmpty(icms.getAliquotaAplicavelCalculoCredito())){
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "ICMS - Aliquota inválida!");
			}
			retorno = false;
		}
		return retorno;
	}

}
