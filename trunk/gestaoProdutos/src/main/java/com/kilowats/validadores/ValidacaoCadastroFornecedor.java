package com.kilowats.validadores;

import java.util.ArrayList;
import java.util.List;

import com.kilowats.entidades.Empresa;
import com.kilowats.interfaces.IValidacaoCadastro;
import com.kilowats.utils.Utils;

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
	public List<String> validarCadastroComMensagem(Object obj) {
		Empresa empresa = (Empresa) obj;
		List<String> mensagens = new ArrayList<>();
		if (Utils.isNullOrEmpty(empresa.getNome())) {
			mensagens.add("Nome não está preenchido");
		}
		if (!cpfCgcValido(empresa.getCgcCpf().replaceAll("\\D", ""))) {
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
