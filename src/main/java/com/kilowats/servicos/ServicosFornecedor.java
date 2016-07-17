package com.kilowats.servicos;

import java.io.Serializable;

import javax.inject.Inject;

import com.kilowats.annotations.CadastrarFornecedor;
import com.kilowats.annotations.ValidarFornecedor;
import com.kilowats.entidades.Empresa;
import com.kilowats.interfaces.IPersistirBancoDados;
import com.kilowats.interfaces.IValidacaoCadastro;

public class ServicosFornecedor implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject @CadastrarFornecedor
	private IPersistirBancoDados persistir;
	@Inject @ValidarFornecedor
	private IValidacaoCadastro validar;
	
	public boolean persistirFornecedor(Empresa empresa){
		return persistir.salvar(empresa);
	}
	public boolean validarFornecedor(Empresa empresa, String titulo){
		return validar.validarCadastroComMensagem(empresa, titulo);
	}
}
