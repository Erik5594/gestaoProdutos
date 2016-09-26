package com.kilowats.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.kilowats.entidades.Fornecedor;


public class FornecedorDao {
	
	@Inject
	private EntityManager manager;
	
	public Fornecedor salvarOrUpdate(Fornecedor empresa) {
		EntityTransaction entityTransaction = manager.getTransaction();
		entityTransaction.begin();
		empresa = (Fornecedor) manager.merge(empresa);
		entityTransaction.commit();
		return empresa;
	}
}
