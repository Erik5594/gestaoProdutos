package com.github.erik5594.controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import lombok.Getter;
import lombok.Setter;

import com.github.erik5594.entidades.Grupo;
import com.github.erik5594.entidades.Usuario;
import com.github.erik5594.servicos.ServicosGrupo;
import com.github.erik5594.servicos.ServicosUsuario;
import com.github.erik5594.util.Utils;
import com.github.erik5594.util.jsf.FacesUtils;

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
	
	private String[] ids;
	
	
	public void inicializar(){
		if(FacesUtils.isNotPostback()){
			grupos = new ArrayList<>();
			grupos = servicosGrupo.todos();
		}
	}
	public void salvar(){
		setarGruposUsuario();
		usuario = servicosUsuario.salvar(usuario, titulo, true);
		if(Utils.isNotNullOrEmpty(usuario) && Utils.isNotNullOrEmpty(usuario.getId()) && usuario.getId() > 0){
			FacesUtils.sendMensagemOk(titulo, "Usuário cadastrado com sucesso!");
			limpar();
		}else{
			FacesUtils.sendMensagemError(titulo, "Usuário não cadastrado!");
		}
	}

	private void setarGruposUsuario() {
		preencherGruposSelecionados();
		usuario.setGrupos(gruposSelecionado);
	}
	private void preencherGruposSelecionados() {
		gruposSelecionado = new ArrayList<>();
		for(String id : ids){
			Grupo grupo = new Grupo();
			grupo = servicosGrupo.pesquisarById(new Long(id));
			if(grupo.getId() > 0){
				gruposSelecionado.add(grupo);
			}
		}
	}
	
	private void limpar() {
		usuario = new Usuario();
		gruposSelecionado = new ArrayList<>();
		ids = null;
	}
	public String[] getIds() {
		return ids;
	}
	public void setIds(String[] ids) {
		this.ids = ids;
	}
}
