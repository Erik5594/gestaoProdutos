package com.kilowats.servicos;

import com.kilowats.interfaces.IPersistirBancoDados;

public class ServicosProduto implements IPersistirBancoDados {

	@Override
	public boolean salvar(Object obj) {
		return false;
	}

	@Override
	public boolean alterar(Object obj) {
		return false;
	}

	@Override
	public boolean deletar(Object obj) {
		return false;
	}

}
