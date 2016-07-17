package com.kilowats.servicos;

import java.io.Serializable;

import javax.inject.Inject;

import com.kilowats.annotations.ValidarEan;
import com.kilowats.entidades.Ean;
import com.kilowats.interfaces.IValidacaoCadastro;

public class ServicosEan implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject @ValidarEan
	private IValidacaoCadastro validador;
	
	public boolean eanIsValido(Ean ean, String titulo){
		return validador.validarCadastroComMensagem(ean, titulo);
	}

}
