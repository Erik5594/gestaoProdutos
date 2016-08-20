package com.kilowats.dao;

import com.kilowats.annotations.CadastrarFornecedor;
import com.kilowats.interfaces.IPersistirBancoDados;

@CadastrarFornecedor
public class FornecedorDao implements IPersistirBancoDados{

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
