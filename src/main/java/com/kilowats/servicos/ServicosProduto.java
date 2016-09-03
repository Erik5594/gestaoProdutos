package com.kilowats.servicos;

import java.io.Serializable;

import javax.inject.Inject;

import com.kilowats.annotations.ValidarProduto;
import com.kilowats.dao.ProdutoDao;
import com.kilowats.entidades.Produto;
import com.kilowats.interfaces.IValidacaoCadastro;

public class ServicosProduto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ProdutoDao produtoDao;
	@Inject @ValidarProduto
	private IValidacaoCadastro validador;

	public boolean persistirProduto(Produto produto) {
		return false;
	}
	
	public boolean produtoIsValido(Produto produto, String titulo, boolean mostrarMensagem) {
		return validador.validarCadastroComMensagem(produto, titulo, mostrarMensagem);
	}
}
