package com.kilowats.servicos;

import java.io.Serializable;
import java.util.List;

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
	
	public void persistirListaCidade(List<Cidade> cidades){
		cidadeDao.salvarOrUpdateLista(cidades);
	}
	
	public void saveCidade(Cidade cidade){
		cidadeDao.salvar(cidade);
	}
	
	public void saveListaCidade(List<Cidade> cidades){
		cidadeDao.salvarLista(cidades);
	}
	
	public Cidade pesquisarByNomeCidade(String nomeCidade){
		return cidadeDao.pesquisarByNome(nomeCidade);
	}
	
	public boolean cidadeIsValido(Cidade cidade, String titulo, boolean mostrarMensagem){
		return validador.validarCadastroComMensagem(cidade, titulo, mostrarMensagem);
	}
	
	public Cidade pesquisarById(long id){
		return cidadeDao.pesquisarById(id);
	}
	
	public Cidade pesquisarMunicipioByFaixaCep(long cep){
		return cidadeDao.pesquisarMunicipioByFaixaCep(cep);
	}
	
	public Cidade pesquisarMunicipioByCepGeral(long cep){
		return cidadeDao.pesquisarMunicipioByCepGeral(cep);
	}
}
