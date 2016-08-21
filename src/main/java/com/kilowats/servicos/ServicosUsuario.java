package com.kilowats.servicos;

import java.io.Serializable;

import javax.inject.Inject;

import com.kilowats.annotations.CadastrarUsuario;
import com.kilowats.annotations.ValidarUsuario;
import com.kilowats.entidades.Usuario;
import com.kilowats.interfaces.IPersistirBancoDados;
import com.kilowats.interfaces.IValidacaoCadastro;

public class ServicosUsuario implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject @ValidarUsuario
	private IValidacaoCadastro validar;
	@Inject @CadastrarUsuario
	private IPersistirBancoDados persistir;
	
	public boolean usuarioIsValido(Usuario usuario, String titulo){
		return validar.validarCadastroComMensagem(usuario, titulo);
	}
	
	public Usuario salvar(Usuario usuario){
		return (Usuario) persistir.salvar(usuario);
	}
	
	public Usuario pesquisarById(Usuario usuario){
		return (Usuario) persistir.pesquisarById(usuario.getId());
	}
	
	public boolean deletar(Usuario usuario) {
		return persistir.deletar(usuario);
	}

}
