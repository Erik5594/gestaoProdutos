package com.github.erik5594.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.github.erik5594.entidades.Chip;


public class ChipDao {
	
	@Inject
	private EntityManager manager;
	
	public Chip salvarOrUpdate(Chip chip) {
		EntityTransaction entityTransaction = manager.getTransaction();
		entityTransaction.begin();
		chip = (Chip) manager.merge(chip);
		entityTransaction.commit();
		return chip;
	}
}
