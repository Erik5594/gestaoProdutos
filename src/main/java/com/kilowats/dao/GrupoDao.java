package com.kilowats.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.kilowats.entidades.Grupo;

public class GrupoDao {
	@Inject
	private EntityManager manager;

	public Grupo salvar(Grupo grupo) {
		return (Grupo) manager.merge(grupo);
	}

	public boolean deletar(Grupo grupo) {
		boolean retorno = true;
		try{
			manager.remove(grupo);
		}catch(Exception e){
			retorno = false;
		}
		return retorno;
	}

	public Grupo pesquisarById(Long id) {
		return (Grupo) manager.find(Grupo.class, id);
	}
	
	public List<Grupo> todos(){
		return manager.createQuery("from Grupo", Grupo.class).getResultList();
	}
}
