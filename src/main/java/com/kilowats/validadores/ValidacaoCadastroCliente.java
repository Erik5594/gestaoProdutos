package com.kilowats.validadores;

import com.kilowats.annotations.ValidarCliente;
import com.kilowats.entidades.Cliente;
import com.kilowats.interfaces.IValidacaoCadastro;
import com.kilowats.utils.Utils;
import com.kilowats.util.jsf.FacesUtils;

@ValidarCliente
public class ValidacaoCadastroCliente implements IValidacaoCadastro {

	@Override
	public boolean validarCadastro(Object obj) {
		Cliente cliente = (Cliente) obj;
		if (cliente != null) {
			if (Utils.isNullOrEmpty(cliente.getNome())) {
				return false;
			}
			if (!cpfCgcValido(cliente.getCgcCpf().replaceAll("\\D", ""))) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	@Override
	public boolean validarCadastroComMensagem(Object obj, String titulo) {
		Cliente cliente = (Cliente) obj;
		boolean retorno = true;
			if (Utils.isNullOrEmpty(cliente.getNome())) {
				FacesUtils
						.sendMensagemError(titulo, "Nome não está preenchido");
				retorno = false;
			}
			if (!cpfCgcValido(cliente.getCgcCpf().replaceAll("\\D", ""))) {
				FacesUtils.sendMensagemError(titulo, "CPF ou CNPJ inválido");
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
