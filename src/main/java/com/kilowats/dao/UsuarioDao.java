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
	public Usuario salvar(Object obj) {
		if(obj instanceof Usuario){
			Usuario usuario = (Usuario) obj;
			return manager.merge(usuario);
		}
		return null;
	}	

	@Override
	public boolean deletar(Object obj) {
		// TODO Auto-generated method stub
		return false;
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
	
}
