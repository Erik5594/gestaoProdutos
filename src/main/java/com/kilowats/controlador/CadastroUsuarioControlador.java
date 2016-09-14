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
import com.kilowats.util.Utils;
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
	
	private String[] ids;
	
	
	public void inicializar(){
		System.out.println("Inicializando...");
		if(FacesUtils.isNotPostback()){
			grupos = new ArrayList<>();
			grupos = servicosGrupo.todos();
		}
	}
	public void salvar(){
		setarGruposUsuario();
		if(servicosUsuario.usuarioIsValido(usuario, titulo, true)){
			usuario = servicosUsuario.salvar(usuario);
			if(Utils.isNotNull(usuario) && usuario.getId() > 0){
				FacesUtils.sendMensagemOk(titulo, "Usuário cadastrado com sucesso!");
				limpar();
			}else{
				FacesUtils.sendMensagemError(titulo, "Usuário não cadastrado!");
			}
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
	}
	public String[] getIds() {
		return ids;
	}
	public void setIds(String[] ids) {
		this.ids = ids;
	}
}
