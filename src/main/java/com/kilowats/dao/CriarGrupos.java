package com.kilowats.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.kilowats.entidades.Grupo;

public class CriarGrupos {

	public static void main(String[] args) {
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

	}

}
