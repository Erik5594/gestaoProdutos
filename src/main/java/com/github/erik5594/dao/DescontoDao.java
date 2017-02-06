package com.github.erik5594.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.github.erik5594.entidades.Desconto;

public class DescontoDao {

	@Inject
	private EntityManager manager;

	public Desconto salvarOrUpdate(Desconto desconto) {
		EntityTransaction entityTransaction = manager.getTransaction();
		entityTransaction.begin();
		desconto = (Desconto) manager.merge(desconto);
		entityTransaction.commit();
		return desconto;
	}

	public Desconto pesquisarById(Long id) {
		return (Desconto) manager.find(Desconto.class, id);
	}

	public List<Desconto> listarTodosDescontos() {
		return manager.createQuery("from Desconto", Desconto.class)
				.getResultList();
	}

	public void salvarOrUpdateLista(List<Desconto> descontos) {
		EntityTransaction entityTransaction = manager.getTransaction();
		entityTransaction.begin();
		for (Desconto desconto : descontos) {
			desconto = manager.merge(desconto);
		}
		entityTransaction.commit();
	}
	
	public List<Desconto> buscarDescontoByNome(String nome) {
		return this.manager.createQuery("from Desconto where upper(nome) like :nome and ativo = :ativo", Desconto.class)
				.setParameter("ativo", true)
				.setParameter("nome", nome.toUpperCase() + "%").getResultList();
	}
}
