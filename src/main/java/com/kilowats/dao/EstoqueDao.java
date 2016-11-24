package com.kilowats.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.kilowats.entidades.EstoqueProduto;

public class EstoqueDao {

	@Inject
	private EntityManager manager;

	public EstoqueProduto salvarOrUpdate(EstoqueProduto estoqueProduto) {
		EntityTransaction entityTransaction = manager.getTransaction();
		entityTransaction.begin();
		estoqueProduto = (EstoqueProduto) manager.merge(estoqueProduto);
		entityTransaction.commit();
		return estoqueProduto;
	}
}
