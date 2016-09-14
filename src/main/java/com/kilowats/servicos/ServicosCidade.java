package com.kilowats.servicos;

import java.io.Serializable;

import javax.inject.Inject;

import com.kilowats.annotations.ValidarCidade;
import com.kilowats.dao.CidadeDao;
import com.kilowats.entidades.Cidade;
import com.kilowats.interfaces.IValidacaoCadastro;

public class ServicosCidade implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject @ValidarCidade
	private IValidacaoCadastro validador;
	@Inject
	private CidadeDao cidadeDao;
	
	public Cidade persistirCidade(Cidade cidade){
		return cidadeDao.salvarOrUpdate(cidade);
	}
	
	public Cidade pesquisarByNomeCidade(String nomeCidade){
		return cidadeDao.pesquisarByNome(nomeCidade);
	}
	
	public boolean cidadeIsValido(Cidade cidade, String titulo, boolean mostrarMensagem){
		return validador.validarCadastroComMensagem(cidade, titulo, mostrarMensagem);
	}
}
