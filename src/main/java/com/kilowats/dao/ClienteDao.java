package com.kilowats.dao;

import com.kilowats.annotations.CadastrarCliente;
import com.kilowats.interfaces.IPersistirBancoDados;

@CadastrarCliente
public class ClienteDao implements IPersistirBancoDados{

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
