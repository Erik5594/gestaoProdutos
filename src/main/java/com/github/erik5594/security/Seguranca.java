package com.github.erik5594.security;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Named
@RequestScoped
public class Seguranca {
	
	@Inject
	private ExternalContext externalContext;
	
	public String getNome() {
		String nome = null;
		
		UsuarioSistema usuarioSistema = getUsuarioLogado();
		if(usuarioSistema != null){
			nome = usuarioSistema.getUsuario().getNome();
		}
		return nome;
	}

	private UsuarioSistema getUsuarioLogado() {
		UsuarioSistema usuario = null;
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) 
		FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
		
		if(auth != null && auth.getPrincipal() != null){
			usuario = (UsuarioSistema) auth.getPrincipal();
		}
		return usuario;
	}

	public boolean isEditarEstoqueProduto() {
		return externalContext.isUserInRole("ADMINISTRADORES");
	}
}
