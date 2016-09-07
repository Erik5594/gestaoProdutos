package com.kilowats.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.kilowats.entidades.Cep;

public class CepDao {

	@Inject
	private EntityManager manager;
	
	public Cep salvar(Cep cep) {
		EntityTransaction entityTransaction = manager.getTransaction();
		entityTransaction.begin();
		cep = (Cep) manager.merge(cep);
		entityTransaction.commit();
		return cep;
	}

	public Cep pesquisarCepByCep(Long numrCep) {
		return (Cep) manager.find(Cep.class, numrCep);
	}
}
