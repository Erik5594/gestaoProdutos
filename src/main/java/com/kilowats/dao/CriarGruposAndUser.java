package com.kilowats.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.kilowats.entidades.Grupo;
import com.kilowats.entidades.Usuario;

public class CriarGruposAndUser {

	public static void main(String[] args) {
		criarUsuarios();
		criarAdministrador();
	}

	private static void criarUsuarios() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("gestaoPU");
		EntityManager manager = factory.createEntityManager();
		EntityTransaction trc = manager.getTransaction();
		trc.begin();
		
		Grupo grupo = new Grupo();
		grupo.setDescricao("Administrador do sistema.");
		grupo.setNome("ADMINISTRADORES");
		grupo.setNomeAmigavel("Administradores");
		manager.merge(grupo);
		
		grupo = new Grupo();
		grupo.setDescricao("Financeiro da empresa.");
		grupo.setNome("FINANCEIROS");
		grupo.setNomeAmigavel("Financeiros");
		manager.merge(grupo);
		
		grupo = new Grupo();
		grupo.setDescricao("Vendedores da empresa.");
		grupo.setNome("VENDEDORES");
		grupo.setNomeAmigavel("Vendedores");
		manager.merge(grupo);
		
		grupo = new Grupo();
		grupo.setDescricao("Técnico da empresa.");
		grupo.setNome("TECNICOS");
		grupo.setNomeAmigavel("Técnicos");
		manager.merge(grupo);
		
		trc.commit();
		manager.close();
		factory.close();
	}
	
	private static void criarAdministrador() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("gestaoPU");
		EntityManager manager = factory.createEntityManager();
		EntityTransaction trc = manager.getTransaction();
		trc.begin();
		
		Usuario usuario = new Usuario();
		usuario.setEmail("admin");
		usuario.setNome("Erik");
		usuario.setSenha("admin");
		usuario.setSenhaConfirmacao("admin");
		
		Grupo grupo = manager.find(Grupo.class, 1L);
		
		List<Grupo> grupos = new ArrayList<>();
		grupos.add(grupo);
		
		usuario.setGrupos(grupos);
		
		manager.merge(usuario);
		trc.commit();
		manager.close();
		factory.close();
	}

}
