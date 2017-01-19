package com.github.erik5594.servicos;

import java.io.Serializable;

import javax.inject.Inject;

import com.github.erik5594.annotations.ValidarEndereco;
import com.github.erik5594.dao.EnderecoDao;
import com.github.erik5594.entidades.Endereco;
import com.github.erik5594.interfaces.IValidacaoCadastro;

public class ServicosEndereco implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject @ValidarEndereco
	private IValidacaoCadastro validador;
	@Inject
	private EnderecoDao enderecoDao;
	
	public boolean enderecoIsValido(Endereco endereco, String titulo, boolean mostrarMensagem){
		return validador.validarCadastroComMensagem(endereco, titulo, mostrarMensagem);
	}
	
	public Object persistirEndereco(Object endereco){
		return enderecoDao.salvarOrUpdate(endereco);
	}

}
