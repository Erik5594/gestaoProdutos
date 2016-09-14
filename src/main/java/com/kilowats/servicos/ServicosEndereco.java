package com.kilowats.servicos;

import java.io.Serializable;

import javax.inject.Inject;

import com.kilowats.annotations.ValidarEndereco;
import com.kilowats.dao.EnderecoDao;
import com.kilowats.entidades.Endereco;
import com.kilowats.interfaces.IValidacaoCadastro;

public class ServicosEndereco implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject @ValidarEndereco
	private IValidacaoCadastro validador;
	@Inject
	private EnderecoDao enderecoDao;
	
	public boolean enderecoIsValido(Endereco endereco, String titulo, boolean mostrarMensagem){
		return validador.validarCadastroComMensagem(endereco, titulo, mostrarMensagem);
	}
	
	public Endereco persistirEndereco(Endereco endereco){
		return enderecoDao.salvarOrUpdate(endereco);
	}

}
