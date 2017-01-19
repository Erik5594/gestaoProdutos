package com.github.erik5594.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

import com.github.erik5594.entidades.Usuario;

public class UsuarioDao {

	@Inject
	private EntityManager manager;

	public Usuario salvarOrUpdate(Usuario usuario) {
		EntityTransaction entityTransaction = manager.getTransaction();
		entityTransaction.begin();
		usuario = (Usuario) manager.merge(usuario);
		entityTransaction.commit();
		return usuario;
	}

	public boolean deletar(Usuario usuario) {
		boolean retorno = true;
		try {
			manager.remove(usuario);
		} catch (Exception e) {
			retorno = false;
		}
		return retorno;
	}

	public Usuario porEmail(String email) {
		try {
			return manager
					.createQuery("from Usuario where upper(email) = :email",
							Usuario.class)
					.setParameter("email", email.toUpperCase())
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Usuario pesquisarById(Long id) {
		return (Usuario) manager.find(Usuario.class, id);
	}

}
