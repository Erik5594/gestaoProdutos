package com.kilowats.validadores;

import com.kilowats.annotations.ValidarCidade;
import com.kilowats.entidades.Cidade;
import com.kilowats.interfaces.IValidacaoCadastro;
import com.kilowats.util.Utils;
import com.kilowats.util.jsf.FacesUtils;

@ValidarCidade
public class ValidacaoCadastroCidade implements IValidacaoCadastro {

	@Override
	public boolean validarCadastroComMensagem(Object obj, String titulo, boolean mostrarMensagem) {
		Cidade cidade = (Cidade) obj;
		boolean retorno = true;
		if (cidade != null) {
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
			if(!isStringEnderecoValida(cidade.getSiglaCidade())){
				if(mostrarMensagem){
					FacesUtils.sendMensagemError(titulo, "Sigla da cidade inválida");
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
