package com.kilowats.servicos;

import java.io.Serializable;

import javax.inject.Inject;

import com.kilowats.annotations.ValidarFornecedor;
import com.kilowats.dao.FornecedorDao;
import com.kilowats.entidades.Empresa;
import com.kilowats.interfaces.IValidacaoCadastro;

public class ServicosFornecedor implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private FornecedorDao fornecedorDao;
	@Inject @ValidarFornecedor
	private IValidacaoCadastro validar;
	
	public boolean persistirFornecedor(Empresa empresa){
		return false;
	}
	public boolean validarFornecedor(Empresa empresa, String titulo, boolean mostrarMensagem){
		return validar.validarCadastroComMensagem(empresa, titulo, mostrarMensagem);
	}
}
