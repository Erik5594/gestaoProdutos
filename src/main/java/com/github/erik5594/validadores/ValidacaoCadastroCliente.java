package com.github.erik5594.validadores;

import com.github.erik5594.annotations.ValidarCliente;
import com.github.erik5594.entidades.Cliente;
import com.github.erik5594.importacao.intertrack.entidades.ClienteImportacaoIntertrack;
import com.github.erik5594.interfaces.IValidacaoCadastro;
import com.github.erik5594.util.Utils;
import com.github.erik5594.util.jsf.FacesUtils;

@ValidarCliente
public class ValidacaoCadastroCliente implements IValidacaoCadastro {

	@Override
	public boolean validarCadastroComMensagem(Object obj, String titulo, boolean mostrarMensagem) {
		if(obj instanceof Cliente){
			return validarClienteCadastroLocal(obj, titulo, mostrarMensagem);
		}else if(obj instanceof ClienteImportacaoIntertrack){
			return validarClienteImportacaoIntertrack(obj, titulo, mostrarMensagem);
		}else{
			return false;
		}
	}

	private boolean validarClienteCadastroLocal(Object obj, String titulo,
			boolean mostrarMensagem) {
		Cliente cliente = (Cliente) obj;
		boolean retorno = true;
			if (Utils.isNullOrEmpty(cliente.getNome())) {
				if(mostrarMensagem){
					FacesUtils.sendMensagemError(titulo, "Nome não está preenchido");
				}
				retorno = false;
			}
			if (!cpfCgcValido(cliente.getCgcCpf().replaceAll("\\D", ""))) {
				if(mostrarMensagem){
					FacesUtils.sendMensagemError(titulo, "CPF ou CNPJ inválido");
				}
				retorno = false;
			}
		return retorno;
	}
	
	private boolean validarClienteImportacaoIntertrack(Object obj, String titulo,
			boolean mostrarMensagem) {
		ClienteImportacaoIntertrack clienteImportacao = (ClienteImportacaoIntertrack) obj;
		boolean retorno = true;
			if (Utils.isNullOrEmpty(clienteImportacao.getNome())) {
				if(mostrarMensagem){
					FacesUtils.sendMensagemError(titulo, "Nome não está preenchido");
				}
				retorno = false;
			}
			if (!cpfCgcValido(clienteImportacao.getCgcCpf().replaceAll("\\D", ""))) {
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
				 if(Utils.isCNPJValido(cpfCnpj)){
					 return true;
				 }
				 return false;
			} else {
				if (Utils.isCPFValido(cpfCnpj)) {
					return true;
				}
				return false;
			}
		}
	}
}
