package com.kilowats.validadores;

import com.kilowats.annotations.ValidarProduto;
import com.kilowats.entidades.Produto;
import com.kilowats.interfaces.IValidacaoCadastro;
import com.kilowats.utils.Utils;
import com.kilowats.utils.UtilsFaces;

@ValidarProduto
public class ValidacaoCadastroProduto implements IValidacaoCadastro {

	@Override
	public boolean validarCadastro(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean validarCadastroComMensagem(Object obj, String titulo) {
		Produto produto = (Produto) obj;
		boolean retorno = true;
		if(produto == null){
			UtilsFaces.sendMensagemError(titulo, "Produto está vazio!");
			retorno = false;
		}
		if(Utils.isNullOrEmpty(produto.getNomeProduto())){
			UtilsFaces.sendMensagemError(titulo, "Nome do produto inválido!");
			retorno = false;
		}
		if(produto.getTipoUnidade() == null){
			UtilsFaces.sendMensagemError(titulo, "Unidade de medida do produto inválida");
			retorno = false;
		}
		if(!Utils.isNullOrEmpty(produto.getCodProduto()) && produto.getCodProduto().length() > 10){
			UtilsFaces.sendMensagemError(titulo, "Código do produto muito longo. (máximo 10 caracteres)");
			retorno = false;
		}
		return retorno;
	}

}
