package com.kilowats.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.kilowats.entidades.OrdemServico;

public class OrdemServicoDao {

	@Inject
	private EntityManager manager;

	public OrdemServico salvarOrUpdate(OrdemServico ordemServico) {
		EntityTransaction entityTransaction = manager.getTransaction();
		entityTransaction.begin();
		ordemServico = (OrdemServico) manager.merge(ordemServico);
		entityTransaction.commit();
		return ordemServico;
	}

	public OrdemServico pesquisarById(Long id) {
		return (OrdemServico) manager.find(OrdemServico.class, id);
	}

	public List<OrdemServico> listarTodasOrdemServico() {
		return manager.createQuery("from OrdemServico", OrdemServico.class)
				.getResultList();
	}

}
