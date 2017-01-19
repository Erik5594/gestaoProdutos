package com.github.erik5594.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.github.erik5594.entidades.Grupo;

public class GrupoDao {
	@Inject
	private EntityManager manager;

	public Grupo salvarOrUpdate(Grupo grupo) {
		EntityTransaction entityTransaction = manager.getTransaction();
		entityTransaction.begin();
		grupo = (Grupo) manager.merge(grupo);
		entityTransaction.commit();
		return grupo;
	}

	public boolean deletar(Grupo grupo) {
		boolean retorno = true;
		try {
			manager.remove(grupo);
		} catch (Exception e) {
			retorno = false;
		}
		return retorno;
	}

	public Grupo pesquisarById(Long id) {
		return (Grupo) manager.find(Grupo.class, id);
	}

	public List<Grupo> todos() {
		return manager.createQuery("from Grupo", Grupo.class).getResultList();
	}
}
