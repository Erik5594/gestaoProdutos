package com.kilowats.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.kilowats.entidades.Usuario;

public class UsuarioDao {
	
	@Inject
	private EntityManager manager;

	public Usuario salvar(Usuario usuario) {
		return (Usuario) manager.merge(usuario);
	}	

	public boolean deletar(Usuario usuario) {
		boolean retorno = true;
		try{
			manager.remove(usuario);
		}catch(Exception e){
			retorno = false;
		}
		return retorno;
	}
	

	public Usuario porEmail(String email) {
		try {
			return manager.createQuery("from Usuario where upper(email) = :email", Usuario.class)
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
