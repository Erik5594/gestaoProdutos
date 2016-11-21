package com.kilowats.validadores;

import static com.kilowats.util.Utils.isNullOrEmpty;

import com.kilowats.annotations.ValidarValoresProduto;
import com.kilowats.entidades.Ipi;
import com.kilowats.interfaces.IValidacaoCadastro;
import com.kilowats.util.jsf.FacesUtils;

@ValidarValoresProduto
public class ValidacaoIpi implements IValidacaoCadastro {

	@Override
	public boolean validarCadastroComMensagem(Object obj, String titulo, boolean mostrarMensagem) {
		Ipi ipi = (Ipi) obj;
		boolean retorno = true;
		if(isNullOrEmpty(ipi)){
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "Ipi não informado!");
			}
			retorno = false;
		}
		if(isNullOrEmpty(ipi.getProduto())){
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "Ipi sem produto!");
			}
			retorno = false;
		}
		if(isNullOrEmpty(ipi.getClasseEnquadramento())){
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "Classe enquadramento inválida!");
			}
			retorno = false;
		}
		if(isNullOrEmpty(ipi.getCnpjProduto())){
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "Cnpj produto inválido!");
			}
			retorno = false;
		}
		if(isNullOrEmpty(ipi.getCodEnquadramentoLegal())){
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "Código de enquadramento inválido!");
			}
			retorno = false;
		}
		return retorno;
	}

}
