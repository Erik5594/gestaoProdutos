package com.kilowats.validadores;

import org.apache.commons.lang3.StringUtils;

import com.kilowats.annotations.ValidarUsuario;
import com.kilowats.entidades.Usuario;
import com.kilowats.interfaces.IValidacaoCadastro;
import com.kilowats.util.Utils;
import com.kilowats.util.jsf.FacesUtils;

@ValidarUsuario
public class ValidacaoCadastroUsuario implements IValidacaoCadastro {

	@Override
	public boolean validarCadastroComMensagem(Object obj, String titulo, boolean mostrarMensagem) {
		boolean retorno = true;
		Usuario usuario = (Usuario) obj;
		if(usuario != null){
			if(!Utils.isNomeValido(usuario.getEmail())){
				if(mostrarMensagem){
					FacesUtils.sendMensagemError(titulo, "E-mail do usuário inválido.");
				}
				retorno = false;
			}
			
			if(!Utils.isNomeValido(usuario.getNome())){
				if(mostrarMensagem){
					FacesUtils.sendMensagemError(titulo, "Nome do usuário inválido.");
				}
				retorno = false;
			}
			
			if(StringUtils.isNotBlank(usuario.getSenha()) && usuario.getSenha().length() < 4){
				if(mostrarMensagem){
					FacesUtils.sendMensagemError(titulo, "Senha inválida.");
				}
				retorno = false;
			}else{
				if(senhaDiferenteSenhaConfirmacao(usuario)){
					if(mostrarMensagem){
						FacesUtils.sendMensagemError(titulo, "Senha está diferente da Senha de Confirmação.");
					}
					retorno = false;
				}
			}
			
			if(usuario.getGrupos() == null || usuario.getGrupos().isEmpty()){
				if(mostrarMensagem){
					FacesUtils.sendMensagemError(titulo, "Deve ser selecionado ao menos um grupo.");
				}
				retorno = false;
			}
		}else{
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "Usuário está em branco.");
			}
			retorno = false;
		}
		return retorno;
	}

	private boolean senhaDiferenteSenhaConfirmacao(Usuario usuario){
		return !senhaIgualSenhaConfirmacao(usuario);
	}
	
	private boolean senhaIgualSenhaConfirmacao(Usuario usuario) {
		return StringUtils.isNotBlank(usuario.getSenha()) && StringUtils.isNotBlank(usuario.getSenhaConfirmacao()) && usuario.getSenha().equals(usuario.getSenhaConfirmacao());
	}

}
