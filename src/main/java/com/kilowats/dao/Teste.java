package com.kilowats.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.kilowats.entidades.Grupo;
import com.kilowats.entidades.Usuario;
import com.kilowats.enuns.TipoGrupo;

public class Teste {

	
	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("gestaoPU");
		EntityManager manager = factory.createEntityManager();
		EntityTransaction trc = manager.getTransaction();
		trc.begin();
		
		Usuario usuario = new Usuario();
		usuario.setEmail("admin");
		usuario.setNome("Erik");
		usuario.setSenha("admin");
		
		Grupo grupo = manager.find(Grupo.class, 1L);
				
		List<Grupo> grupos = new ArrayList<>();
		grupos.add(grupo);
		
		usuario.setGrupos(grupos);
		
		manager.merge(usuario);
		trc.commit();
		manager.close();
	}

}
