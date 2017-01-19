package com.github.erik5594.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.github.erik5594.entidades.EstoqueProduto;

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
