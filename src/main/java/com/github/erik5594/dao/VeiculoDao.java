package com.github.erik5594.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.github.erik5594.entidades.Veiculo;

public class VeiculoDao {

	@Inject
	private EntityManager manager;

	public Veiculo salvarOrUpdate(Veiculo veiculo) {
		EntityTransaction entityTransaction = manager.getTransaction();
		entityTransaction.begin();
		veiculo = (Veiculo) manager.merge(veiculo);
		entityTransaction.commit();
		return veiculo;
	}

	public Veiculo pesquisarById(Long id) {
		return (Veiculo) manager.find(Veiculo.class, id);
	}

}
