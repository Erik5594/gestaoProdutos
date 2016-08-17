package com.kilowats.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.kilowats.entidades.Chip;
import com.kilowats.entidades.Grupo;
import com.kilowats.entidades.Rastreador;
import com.kilowats.entidades.Usuario;
import com.kilowats.enuns.Operadora;

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
		
		manager.persist(usuario);
		
		trc.commit();
		manager.close();
	}

}
