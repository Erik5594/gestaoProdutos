package com.kilowats.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.kilowats.entidades.Cliente;

public class ClienteDao {

	@Inject
	private EntityManager manager;
	
	public Cliente salvar(Cliente cliente) {
		EntityTransaction entityTransaction = manager.getTransaction();
		entityTransaction.begin();
		cliente = (Cliente) manager.merge(cliente);
		entityTransaction.commit();
		return cliente;
	}

	public Cliente pesquisarById(Long id) {
		return (Cliente) manager.find(Cliente.class, id);
	}

}
