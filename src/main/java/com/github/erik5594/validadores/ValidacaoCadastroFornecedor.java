package com.github.erik5594.validadores;

import com.github.erik5594.annotations.ValidarFornecedor;
import com.github.erik5594.entidades.Fornecedor;
import com.github.erik5594.interfaces.IValidacaoCadastro;
import com.github.erik5594.util.Utils;
import com.github.erik5594.util.jsf.FacesUtils;

@ValidarFornecedor
public class ValidacaoCadastroFornecedor implements IValidacaoCadastro {

	@Override
	public boolean validarCadastroComMensagem(Object obj, String titulo, boolean mostrarMensagem) {
		Fornecedor empresa = (Fornecedor) obj;
		boolean retorno = true;
		if (Utils.isNullOrEmpty(empresa.getNome())) {
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "Nome não está preenchido");
			}
			retorno = false;
		}
		if (!cpfCgcValido(empresa.getCgcCpf().replaceAll("\\D", ""))) {
			if(mostrarMensagem){
				FacesUtils.sendMensagemError(titulo, "CPF ou CNPJ inválido");
			}
			retorno = false;
		}
		return retorno;
	}

	private boolean cpfCgcValido(String cpfCnpj) {
		if (Utils.isNullOrEmpty(cpfCnpj)) {
			return false;
		} else {
			if (Utils.isCGC(cpfCnpj)) {
				// if(Utils.isCNPJValido(cpfCnpj)){
				return true;
				// }
				// return false;
			} else {
				if (Utils.isCPFValido(cpfCnpj)) {
					return true;
				}
				return false;
			}
		}
	}
}
