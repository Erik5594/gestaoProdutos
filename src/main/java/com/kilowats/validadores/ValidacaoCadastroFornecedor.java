package com.kilowats.validadores;

import com.kilowats.annotations.ValidarFornecedor;
import com.kilowats.entidades.Empresa;
import com.kilowats.interfaces.IValidacaoCadastro;
import com.kilowats.utils.Utils;
import com.kilowats.utils.UtilsFaces;

@ValidarFornecedor
public class ValidacaoCadastroFornecedor implements IValidacaoCadastro {

	@Override
	public boolean validarCadastro(Object obj) {
		Empresa empresa = (Empresa) obj;
		if (empresa != null) {
			if (Utils.isNullOrEmpty(empresa.getNome())) {
				return false;
			}
			if (!cpfCgcValido(empresa.getCgcCpf().replaceAll("\\D", ""))) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	@Override
	public boolean validarCadastroComMensagem(Object obj, String titulo) {
		Empresa empresa = (Empresa) obj;
		boolean retorno = true;
		if (Utils.isNullOrEmpty(empresa.getNome())) {
			UtilsFaces.sendMensagemError(titulo, "Nome não está preenchido");
			retorno = false;
		}
		if (!cpfCgcValido(empresa.getCgcCpf().replaceAll("\\D", ""))) {
			UtilsFaces.sendMensagemError(titulo, "CPF ou CNPJ inválido");
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
