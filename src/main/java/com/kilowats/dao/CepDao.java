package com.kilowats.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.kilowats.entidades.Cep;

public class CepDao {

	@Inject
	private EntityManager manager;

	public Cep salvarOrUpdate(Cep cep) {
		EntityTransaction entityTransaction = manager.getTransaction();
		entityTransaction.begin();
		cep = (Cep) manager.merge(cep);
		entityTransaction.commit();
		return cep;
	}

	public void salvar(Cep cep) {
		EntityTransaction entityTransaction = manager.getTransaction();
		entityTransaction.begin();
		manager.persist(cep);
		entityTransaction.commit();
	}

	public void salvarOrUpdateLista(List<Cep> ceps) {
		EntityTransaction entityTransaction = manager.getTransaction();
		entityTransaction.begin();
		for (Cep cep : ceps) {
			cep = (Cep) manager.merge(cep);
		}
		entityTransaction.commit();
	}

	public void salvarLista(List<Cep> ceps) {
		EntityTransaction entityTransaction = manager.getTransaction();
		entityTransaction.begin();
		for (Cep cep : ceps) {
			manager.persist(cep);
		}
		entityTransaction.commit();
	}

	public Cep pesquisarCepByCep(Long numrCep) {
		return (Cep) manager.find(Cep.class, numrCep);
	}
}
