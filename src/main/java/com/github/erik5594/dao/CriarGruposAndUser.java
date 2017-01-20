package com.github.erik5594.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.github.erik5594.entidades.Grupo;
import com.github.erik5594.entidades.Usuario;
import com.github.erik5594.util.jpa.EntityManagerProducer;

public class CriarGruposAndUser {
	
	private EntityManager manager = new EntityManagerProducer().createEntityManager();

	public static void main(String[] args) {
		CriarGruposAndUser criador = new CriarGruposAndUser();
		criador.criarUsuarios();
		criador.criarAdministrador();
	}

	private void criarUsuarios() {
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
	}
	
	private void criarAdministrador() {
		EntityTransaction trc = manager.getTransaction();
		trc.begin();
		
		Usuario usuario = new Usuario();
		usuario.setEmail("admin");
		usuario.setNome("Administrador Erik");
		usuario.setSenha("admin");
		
		Grupo grupo = manager.find(Grupo.class, 1L);
		
		List<Grupo> grupos = new ArrayList<>();
		grupos.add(grupo);
		
		usuario.setGrupos(grupos);
		
		manager.merge(usuario);
		trc.commit();
	}

}
