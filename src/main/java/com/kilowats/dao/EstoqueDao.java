package com.kilowats.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.kilowats.entidades.EstoqueProduto;

public class EstoqueDao {

	@Inject
	private EntityManager manager;

	public EstoqueProduto salvarOrUpdate(EstoqueProduto produtoEstoque) {
		EntityTransaction entityTransaction = manager.getTransaction();
		entityTransaction.begin();
		produtoEstoque = (EstoqueProduto) manager.merge(produtoEstoque);
		entityTransaction.commit();
		return produtoEstoque;
	}
}
