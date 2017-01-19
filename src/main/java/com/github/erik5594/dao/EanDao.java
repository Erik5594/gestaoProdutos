package com.github.erik5594.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.github.erik5594.entidades.Ean;

public class EanDao {

	@Inject
	private EntityManager manager;

	public Ean pesquisarEanByCodBarras(String codBarras) {
		return manager
				.createQuery("from Ean where codBarras = :codBarras", Ean.class)
				.setParameter("codBarras", codBarras).getSingleResult();
	}
}
