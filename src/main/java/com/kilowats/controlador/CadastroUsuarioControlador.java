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
import com.kilowats.enuns.TipoGrupo;
import com.kilowats.servicos.ServicosUsuario;
import com.kilowats.util.jsf.FacesUtils;

@Named
@ViewScoped
public class CadastroUsuarioControlador implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject @Getter @Setter
	private Usuario usuario;
	@Inject
	private ServicosUsuario servicos;
	@Getter @Setter
	List<String> nomeGrupos = new ArrayList<>();
	private final String titulo = "Cadastro de Usuário: ";
	
	public void salvar(){
		setarGrupos();
		if(servicos.usuarioIsValido(usuario, titulo)){
			usuario = servicos.salvar(usuario);
			if(usuario != null && usuario.getId() > 0){
				FacesUtils.sendMensagemOk(titulo, "Usuário cadastrado com sucesso!");
			}
		}
	}

	private List<Grupo> listarGrupos() {
		List<Grupo> grupos = new ArrayList<Grupo>();
		if(nomeGrupos != null && !nomeGrupos.isEmpty()){
			for(String nomeGrupo : nomeGrupos){
				Grupo grupo = new Grupo();
				if(){
					grupo.setNomeGrupo(TipoGrupo.ADMINISTRADORES);
					grupo.setDescricao(descricao);
				}
			}
		}
	}
}
