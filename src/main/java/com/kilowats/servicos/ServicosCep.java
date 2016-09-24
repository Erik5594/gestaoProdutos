package com.kilowats.servicos;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.kilowats.annotations.ValidarCep;
import com.kilowats.dao.CepDao;
import com.kilowats.entidades.Cep;
import com.kilowats.interfaces.IValidacaoCadastro;

public class ServicosCep implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject @ValidarCep
	private IValidacaoCadastro validador;
	@Inject
	private CepDao cepDao;
	
	public Cep persistirCep(Cep cep){
		return cepDao.salvarOrUpdate(cep);
	}
	
	public void mergeListaCep(List<Cep> ceps){
		cepDao.salvarOrUpdateLista(ceps);
	}
	
	public void saveCep(Cep cep){
		cepDao.salvar(cep);
	}
	
	public void saveListaCep(List<Cep> ceps){
		cepDao.salvarLista(ceps);
	}
	
	public boolean cepIsValido(Cep cep, String titulo, boolean mostrarMensagem){
		return validador.validarCadastroComMensagem(cep, titulo, mostrarMensagem);
	}
	
	public Cep pesquisarCepByCep(Long numrCep){
		return cepDao.pesquisarCepByCep(numrCep);
	}
}
