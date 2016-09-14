package com.kilowats.servicos;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.kilowats.dao.GrupoDao;
import com.kilowats.entidades.Grupo;

public class ServicosGrupo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private GrupoDao grupoDao;
	
	public Grupo salvar(Grupo grupo){
		return grupoDao.salvarOrUpdate(grupo);
	}
	
	public Grupo pesquisarById(Long id){
		return grupoDao.pesquisarById(id);
	}
	
	public List<Grupo> todos(){
		return grupoDao.todos();
	}
	
	public boolean deletar(Grupo grupo) {
		return grupoDao.deletar(grupo);
	}

}
