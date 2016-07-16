package com.kilowats.servicos;

import com.kilowats.dao.ProdutoDao;
import com.kilowats.entidades.Produto;
import com.kilowats.interfaces.IPersistirBancoDados;

public class ServicosProduto {

	public static boolean persistirProduto(Produto produto) {
		IPersistirBancoDados persistir = new ProdutoDao();
		return persistir.salvar(produto);
	}
}
