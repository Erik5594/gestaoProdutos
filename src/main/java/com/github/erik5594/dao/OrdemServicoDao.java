package com.github.erik5594.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TransactionRequiredException;

import com.github.erik5594.entidades.OrdemServico;
import com.github.erik5594.util.jsf.FacesUtils;

public class OrdemServicoDao {

	@Inject
	private EntityManager manager;

	public OrdemServico salvarOrUpdate(OrdemServico ordemServico) {
		EntityTransaction entityTransaction = manager.getTransaction();
		entityTransaction.begin();
		try{
			ordemServico = (OrdemServico) manager.merge(ordemServico);
		}catch(IllegalArgumentException | TransactionRequiredException ex){
			FacesUtils.sendMensagemOk("Ocorreu um erro: ", "Ocorreu um erro interno");
		}
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
