package com.kilowats.servicos;

import java.io.Serializable;

import javax.inject.Inject;

import com.kilowats.annotations.ValidarUsuario;
import com.kilowats.dao.UsuarioDao;
import com.kilowats.entidades.Usuario;
import com.kilowats.interfaces.IValidacaoCadastro;

public class ServicosUsuario implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject @ValidarUsuario
	private IValidacaoCadastro validar;
	@Inject
	private UsuarioDao usuarioDao;
	
	public boolean usuarioIsValido(Usuario usuario, String titulo, boolean mostrarMensagem){
		return validar.validarCadastroComMensagem(usuario, titulo, mostrarMensagem);
	}
	
	public Usuario salvar(Usuario usuario){
		return usuarioDao.salvarOrUpdate(usuario);
	}
	
	public Usuario pesquisarById(Usuario usuario){
		return usuarioDao.pesquisarById(usuario.getId());
	}
	
	public boolean deletar(Usuario usuario) {
		return usuarioDao.deletar(usuario);
	}

}
