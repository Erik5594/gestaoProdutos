package com.kilowats.validadores;

import java.util.ArrayList;
import java.util.List;

import com.kilowats.entidades.Cliente;
import com.kilowats.interfaces.IValidacaoCadastro;
import com.kilowats.utils.Utils;

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
	public List<String> validarCadastroComMensagem(Object obj) {
		Cliente cliente = (Cliente) obj;
		List<String> mensagens = new ArrayList<>();
		if (Utils.isNullOrEmpty(cliente.getNome())) {
			mensagens.add("Nome não está preenchido");
		}
		if (!cpfCgcValido(cliente.getCgcCpf().replaceAll("\\D", ""))) {
			mensagens.add("CPF ou CNPJ inválido");
		}
		if(mensagens.isEmpty()){
			mensagens.add("OK");
		}
		return mensagens;
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
