package com.kilowats.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import com.kilowats.entidades.Grupo;
import com.kilowats.entidades.Usuario;
import com.kilowats.servicos.ServicosGrupo;
import com.kilowats.servicos.ServicosUsuario;
import com.kilowats.util.jsf.FacesUtils;

@Named
@ViewScoped
public class CadastroUsuarioControlador implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject @Getter @Setter
	private Usuario usuario;
	@Inject
	private ServicosUsuario servicosUsuario;
	@Inject
	private ServicosGrupo servicosGrupo;
	@Getter @Setter
	private List<Grupo> gruposSelecionado;
	@Getter @Setter
	private List<Grupo> grupos;
	private final String titulo = "Cadastro de Usuário: ";
	
	public CadastroUsuarioControlador() {
		limpar();
	}
	
	public void inicializar(){
		if(FacesUtils.isNotPostback()){
			grupos = servicosGrupo.todos();
		}
	}
	public void salvar(){
		setarGruposUsuario();
		if(servicosUsuario.usuarioIsValido(usuario, titulo)){
			usuario = servicosUsuario.salvar(usuario);
			if(usuario != null && usuario.getId() > 0){
				FacesUtils.sendMensagemOk(titulo, "Usuário cadastrado com sucesso!");
			}
		}
	}

	private void setarGruposUsuario() {
		usuario.setGrupos(gruposSelecionado);
	}
	
	private void limpar() {
		usuario = new Usuario();
		grupos = new ArrayList<>();
		gruposSelecionado = new ArrayList<>();
	}


}
