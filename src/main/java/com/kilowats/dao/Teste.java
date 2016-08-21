package com.kilowats.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.kilowats.entidades.Usuario;

public class Teste {

	
	public static void main(String[] args) {
		Usuario usuario = new Usuario();
		usuario.setEmail("teste1");
		usuario.setNome("Teste1");
		usuario.setSenha("teste1");
		salvar(usuario);
	}
	
	public static void salvar(Object obj){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("gestaoPU");
		EntityManager manager = factory.createEntityManager();
		EntityTransaction trc = manager.getTransaction();
		trc.begin();
		manager.merge(obj);
		trc.commit();
		manager.close();
	}

}
