package com.github.erik5594.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class EnderecoDao {

	@Inject
	private EntityManager manager;
	
	public Object salvarOrUpdate(Object endereco) {
		EntityTransaction entityTransaction = manager.getTransaction();
		entityTransaction.begin();
		endereco = manager.merge(endereco);
		entityTransaction.commit();
		return endereco;
	}
}
