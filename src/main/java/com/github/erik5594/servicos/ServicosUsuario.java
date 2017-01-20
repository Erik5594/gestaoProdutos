package com.github.erik5594.servicos;

import java.io.Serializable;

import javax.inject.Inject;

import com.github.erik5594.annotations.ValidarUsuario;
import com.github.erik5594.dao.UsuarioDao;
import com.github.erik5594.entidades.Usuario;
import com.github.erik5594.interfaces.IValidacaoCadastro;

public class ServicosUsuario implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject @ValidarUsuario
	private IValidacaoCadastro validar;
	@Inject
	private UsuarioDao usuarioDao;
	
	public Usuario salvar(Usuario usuario, String titulo, boolean mostrarMensagem){
		if(validar.validarCadastroComMensagem(usuario, titulo, mostrarMensagem)){
			return usuarioDao.salvarOrUpdate(usuario);
		}
		return usuario;
	}
	
	public Usuario pesquisarById(Usuario usuario){
		return usuarioDao.pesquisarById(usuario.getId());
	}
	
	public boolean deletar(Usuario usuario) {
		return usuarioDao.deletar(usuario);
	}

}
