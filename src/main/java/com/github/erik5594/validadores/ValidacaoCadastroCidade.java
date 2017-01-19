package com.github.erik5594.validadores;

import com.github.erik5594.annotations.ValidarCidade;
import com.github.erik5594.entidades.Cidade;
import com.github.erik5594.interfaces.IValidacaoCadastro;
import com.github.erik5594.util.Utils;
import com.github.erik5594.util.jsf.FacesUtils;

@ValidarCidade
public class ValidacaoCadastroCidade implements IValidacaoCadastro {

	@Override
	public boolean validarCadastroComMensagem(Object obj, String titulo, boolean mostrarMensagem) {
		Cidade cidade = (Cidade) obj;
		boolean retorno = true;
		if (Utils.isNotNullOrEmpty(cidade)) {
			if(Utils.isNullOrEmpty(cidade.getUf())){
				if(mostrarMensagem){
					FacesUtils.sendMensagemError(titulo, "UF deve ser informado");
				}
				retorno = false;
			}
			if(!isStringEnderecoValida(cidade.getNomeCidade())){
				if(mostrarMensagem){
					FacesUtils.sendMensagemError(titulo, "Nome cidade inválido");
				}
				retorno = false;
			}
		}else{
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "Cidade deve ser preenchido");
			}
			retorno = false;
		}
		return retorno;
	}

	private boolean isStringEnderecoValida(String texto) {
		if (Utils.isNullOrEmpty(texto)) {
			return false;
		}
		if (texto.toLowerCase().equals("teste")) {
			return false;
		}
		if (texto.length() <= 2) {
			return false;
		}
		return true;
	}
}
