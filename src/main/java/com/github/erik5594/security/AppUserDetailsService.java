package com.github.erik5594.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.github.erik5594.dao.UsuarioDao;
import com.github.erik5594.entidades.Grupo;
import com.github.erik5594.entidades.Usuario;
import com.github.erik5594.util.cdi.CDIServiceLocator;

public class AppUserDetailsService implements UserDetailsService{

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UsuarioDao usuarioD = CDIServiceLocator.getBean(UsuarioDao.class);
		Usuario usuario = usuarioD.porEmail(email);
		UsuarioSistema user = null;
		if(usuario != null){
			user = new UsuarioSistema(usuario, getGrupos(usuario));
		}
		return user;
	}

	private Collection<? extends GrantedAuthority> getGrupos(Usuario usuario) {
		List<SimpleGrantedAuthority> grupos = new ArrayList<>();
		
		for(Grupo grupo : usuario.getGrupos()){
			grupos.add(new SimpleGrantedAuthority(grupo.getNome().toUpperCase()));
		}
		
		return grupos;
	}

}
