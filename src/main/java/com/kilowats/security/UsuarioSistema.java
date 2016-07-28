package com.kilowats.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.kilowats.entidades.Usuario;

public class UsuarioSistema extends User{

	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;

	public UsuarioSistema(Usuario user, Collection<? extends GrantedAuthority> authorities) {
		super(user.getEmail(), user.getSenha(), authorities);
		this.usuario = user;
	}

	public Usuario getUsuario() {
		return usuario;
	}

}
