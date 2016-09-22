package com.kilowats.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

import com.kilowats.entidades.Cidade;

public class CidadeDao {

	@Inject
	private EntityManager manager;
	
	public Cidade salvarOrUpdate(Cidade cidade) {
		EntityTransaction entityTransaction = manager.getTransaction();
		entityTransaction.begin();
		cidade = (Cidade) manager.merge(cidade);
		entityTransaction.commit();
		return cidade;
	}
	
	public void salvar(Cidade cidade) {
		EntityTransaction entityTransaction = manager.getTransaction();
		entityTransaction.begin();
		manager.persist(cidade);
		entityTransaction.commit();
	}
	
	public void salvarLista(List<Cidade> cidades) {
		EntityTransaction entityTransaction = manager.getTransaction();
		entityTransaction.begin();
		for(Cidade cidade : cidades){
			manager.persist(cidade);
		}
		entityTransaction.commit();
	}
	
	public void salvarOrUpdateLista(List<Cidade> cidades) {
		EntityTransaction entityTransaction = manager.getTransaction();
		entityTransaction.begin();
		for(Cidade cidade : cidades){
			cidade = (Cidade) manager.merge(cidade);
		}
		entityTransaction.commit();
	}
	
	public Cidade pesquisarByNome(String nomeCidade){
		try{
			return (Cidade) manager.createQuery("from Cidade where upper(nomeCidade) = :nomeCidade", Cidade.class)
				.setParameter("nomeCidade", nomeCidade.toUpperCase()).getSingleResult();
		}catch (NoResultException e) {
			return null;
		}
	}
	
	public Cidade pesquisarById(long id){
		return (Cidade) manager.find(Cidade.class, id);
	}
	
	public Cidade pesquisarMunicipioByCepGeral(long cep){
		try{
			return (Cidade) manager.createQuery("from Cidade where cepInicial = :cepInicial and cepInicial = cepFinal", Cidade.class)
				.setParameter("cepInicial", cep).getSingleResult();
		}catch (NoResultException e) {
			return null;
		}
	}
	
	public Cidade pesquisarMunicipioByFaixaCep(long cep){
		try{
			return (Cidade) manager.createQuery("from Cidade where :cepInicial BETWEEN cepInicial AND cepFinal", Cidade.class)
				.setParameter("cepInicial", cep).getSingleResult();
		}catch (NoResultException e) {
			return null;
		}
	}
}
