package com.kilowats.validadores;

import com.kilowats.entidades.Empresa;
import com.kilowats.interfaces.IValidacaoCadastro;
import com.kilowats.utils.Utils;

public class ValidacaoCadastroFornecedor implements IValidacaoCadastro{

	@Override
	public boolean validarCadastro(Object obj) {
		Empresa empresa = (Empresa) obj;
		if (empresa != null) {
			if (Utils.isNullOrEmpty(empresa.getNome())) {
				return false;
			}
			if (!cpfCgcValido(empresa)) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}


	@Override
	public String validarCadastroComMensagem(Object obj) {
		Empresa empresa = (Empresa) obj;
		if(Utils.isNullOrEmpty(empresa.getNome())){
			return "Nome não está preenchido";
		}
		if(!cpfCgcValido(empresa)){
			return "CPF ou CNPJ inválido";
		}
		return "OK";
	}

	private boolean cpfCgcValido(Empresa empresa) {
		if(Utils.isNullOrEmpty(empresa.getCgcCpf())){
			return false;
		}else{
			if(Utils.isCGC(empresa.getCgcCpf())){
				if(Utils.isCNPJValido(empresa.getCgcCpf())){
					return true;
				}
				return false;
			}else{
				if(Utils.isCPFValido(empresa.getCgcCpf())){
					return true;
				}
				return false;
			}
		}
	}
}
