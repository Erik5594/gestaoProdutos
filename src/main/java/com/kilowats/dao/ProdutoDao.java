package com.kilowats.dao;

import com.kilowats.annotations.CadastrarProduto;
import com.kilowats.interfaces.IPersistirBancoDados;

@CadastrarProduto
public class ProdutoDao implements IPersistirBancoDados{

	@Override
	public Object salvar(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deletar(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

}
