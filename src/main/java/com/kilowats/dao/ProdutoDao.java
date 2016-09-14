package com.kilowats.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.kilowats.entidades.Produto;


public class ProdutoDao {
	
	@Inject
	private EntityManager manager;
	
	public Produto salvarOrUpdate(Produto produto) {
		EntityTransaction entityTransaction = manager.getTransaction();
		entityTransaction.begin();
		produto = (Produto) manager.merge(produto);
		entityTransaction.commit();
		return produto;
	}
}
