package com.kilowats.servicos;

import java.io.Serializable;

import javax.inject.Inject;

import com.kilowats.annotations.CadastrarProduto;
import com.kilowats.annotations.ValidarProduto;
import com.kilowats.entidades.Produto;
import com.kilowats.interfaces.IPersistirBancoDados;
import com.kilowats.interfaces.IValidacaoCadastro;

public class ServicosProduto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject @CadastrarProduto
	private IPersistirBancoDados persistir;
	@Inject @ValidarProduto
	private IValidacaoCadastro validador;

	public boolean persistirProduto(Produto produto) {
		return false;
	}
	
	public boolean produtoIsValido(Produto produto, String titulo) {
		return validador.validarCadastroComMensagem(produto, titulo);
	}
}
