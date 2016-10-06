package com.kilowats.validadores;

import static com.kilowats.util.Utils.isNullOrEmpty;

import com.kilowats.annotations.ValidarProduto;
import com.kilowats.entidades.Produto;
import com.kilowats.interfaces.IValidacaoCadastro;
import com.kilowats.util.jsf.FacesUtils;

@ValidarProduto
public class ValidacaoCadastroProduto implements IValidacaoCadastro {

	@Override
	public boolean validarCadastroComMensagem(Object obj, String titulo, boolean mostrarMensagem) {
		Produto produto = (Produto) obj;
		boolean retorno = true;
		if(isNullOrEmpty(produto)){
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "Produto está vazio!");
			}
			retorno = false;
		}
		if(isNullOrEmpty(produto.getNomeProduto())){
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "Nome do produto inválido!");
			}
			retorno = false;
		}
		if(isNullOrEmpty(produto.getTipoUnidade())){
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "Unidade de medida do produto inválida");
			}
			retorno = false;
		}
		if(isNullOrEmpty(produto.getCodProduto())){
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "Código do produto inválido.");
			}
			retorno = false;
		}else if(produto.getCodProduto().length() > 10){
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "Código do produto muito longo. (máximo 10 caracteres)");
			}
			retorno = false;
		}
		return retorno;
	}

}
