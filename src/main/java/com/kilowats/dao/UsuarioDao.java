package com.kilowats.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.kilowats.annotations.CadastrarUsuario;
import com.kilowats.entidades.Usuario;
import com.kilowats.interfaces.IPersistirBancoDados;

@CadastrarUsuario
public class UsuarioDao implements IPersistirBancoDados{
	
	@Inject
	private EntityManager manager;

	@Override
	public Object salvar(Object obj) {
		return manager.merge(obj);
	}	

	@Override
	public boolean deletar(Object obj) {
		boolean retorno = true;
		try{
			manager.remove(obj);
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

	@Override
	public Object pesquisarById(Object obj) {
		return manager.find(Usuario.class, obj);
	}
	
}
