package com.kilowats.validadores;

import java.util.ArrayList;
import java.util.List;

import com.kilowats.entidades.Produto;
import com.kilowats.interfaces.IValidacaoCadastro;
import com.kilowats.utils.Utils;

public class ValidacaoCadastroProduto implements IValidacaoCadastro {

	@Override
	public boolean validarCadastro(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> validarCadastroComMensagem(Object obj) {
		List<String> mensagens = new ArrayList<>();
		Produto produto = (Produto) obj;
		if(produto == null){
			mensagens.add("Produto está vazio!");
		}
		if(Utils.isNullOrEmpty(produto.getNomeProduto())){
			mensagens.add("Nome do produto inválido!");
		}
		if(produto.getTipoUnidade() == null){
			mensagens.add("Unidade de medida do produto inválida");
		}
		if(!Utils.isNullOrEmpty(produto.getCodProduto()) && produto.getCodProduto().length() > 10){
			mensagens.add("Código do produto muito longo. (máximo 10 caracteres)");
		}
		if(mensagens.isEmpty()){
			mensagens.add("OK");
		}
		return mensagens;
	}

}
