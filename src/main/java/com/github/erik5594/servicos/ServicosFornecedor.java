package com.github.erik5594.servicos;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.github.erik5594.annotations.ValidarFornecedor;
import com.github.erik5594.dao.FornecedorDao;
import com.github.erik5594.entidades.Fornecedor;
import com.github.erik5594.interfaces.IValidacaoCadastro;

public class ServicosFornecedor implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private FornecedorDao fornecedorDao;
	@Inject @ValidarFornecedor
	private IValidacaoCadastro validar;
	
	public Fornecedor persistirFornecedor(Fornecedor empresa){
		return fornecedorDao.salvarOrUpdate(empresa);
	}
	
	public boolean validarFornecedor(Fornecedor empresa, String titulo, boolean mostrarMensagem){
		return validar.validarCadastroComMensagem(empresa, titulo, mostrarMensagem);
	}
	
	public List<Fornecedor> listarTodosFornecedores() {
		return fornecedorDao.listarTodosFornecedores();
	}
}
