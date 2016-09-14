package com.kilowats.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.kilowats.entidades.Empresa;


public class FornecedorDao {
	
	@Inject
	private EntityManager manager;
	
	public Empresa salvarOrUpdate(Empresa empresa) {
		EntityTransaction entityTransaction = manager.getTransaction();
		entityTransaction.begin();
		empresa = (Empresa) manager.merge(empresa);
		entityTransaction.commit();
		return empresa;
	}
}
