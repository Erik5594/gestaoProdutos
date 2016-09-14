package com.kilowats.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.kilowats.entidades.Endereco;

public class EnderecoDao {

	@Inject
	private EntityManager manager;
	
	public Endereco salvarOrUpdate(Endereco endereco) {
		EntityTransaction entityTransaction = manager.getTransaction();
		entityTransaction.begin();
		endereco = (Endereco) manager.merge(endereco);
		entityTransaction.commit();
		return endereco;
	}
}
