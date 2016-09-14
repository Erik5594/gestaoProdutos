package com.kilowats.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.kilowats.entidades.Chip;


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
