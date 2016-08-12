package com.kilowats.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.kilowats.entidades.Chip;
import com.kilowats.entidades.Rastreador;
import com.kilowats.enuns.Operadora;

public class Teste {

	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("gestaoPU");
		EntityManager manager = factory.createEntityManager();
		EntityTransaction trc = manager.getTransaction();
		trc.begin();
		
		Chip chip = new Chip();
		
		chip.setDdd(62);
		chip.setImei("545131545345312");
		chip.setNumero(985023781);
		chip.setOperadora(Operadora.OI);
		
		Rastreador rastreador = new Rastreador();
		rastreador.setExigeFabricante(false);
		rastreador.setFabricante(null);
		rastreador.setVeiculo(null);
		rastreador.getChip().add(chip);
		
		chip.setRastreador(rastreador);
		
		manager.persist(chip);
		
		trc.commit();
	}

}
