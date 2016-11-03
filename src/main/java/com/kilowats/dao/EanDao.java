package com.kilowats.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.kilowats.entidades.Ean;

public class EanDao {

	@Inject
	private EntityManager manager;

	public Ean pesquisarEanByCodBarras(String codBarras) {
		return manager
				.createQuery("from Ean where codBarras = :codBarras", Ean.class)
				.setParameter("codBarras", codBarras).getSingleResult();
	}
}
