package com.github.erik5594.validadores;

import static com.github.erik5594.util.Utils.isNullOrEmpty;

import com.github.erik5594.annotations.ValidarProduto;
import com.github.erik5594.entidades.Produto;
import com.github.erik5594.interfaces.IValidacaoCadastro;
import com.github.erik5594.util.jsf.FacesUtils;

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
		/*if(isNullOrEmpty(produto.getTipoUnidadeTributavel())){
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "Unidade de medida tributavel do produto inválida");
			}
			retorno = false;
		}*/
		if(isNullOrEmpty(produto.getCodProduto())){
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "Código do produto inválido.");
			}
			retorno = false;
		}else if(produto.getCodProduto().length() > 15){
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "Código do produto muito longo. (máximo 15 caracteres)");
			}
			retorno = false;
		}
		IValidacaoCadastro validador = new ValidacaoValoresProduto();
		if(!validador.validarCadastroComMensagem(produto.getValoresProdutos(), titulo, true)){
			retorno = false;
		}
		return retorno;
	}

}
