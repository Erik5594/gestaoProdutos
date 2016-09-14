package com.kilowats.dao;

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
	
	public Cidade pesquisarByNome(String nomeCidade){
		try{
			return (Cidade) manager.createQuery("from Cidade where upper(nomeCidade) = :nomeCidade", Cidade.class)
				.setParameter("nomeCidade", nomeCidade.toUpperCase()).getSingleResult();
		}catch (NoResultException e) {
			return null;
		}
	}
}
