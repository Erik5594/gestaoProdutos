package com.kilowats.servicos;

import java.io.Serializable;

import javax.inject.Inject;

import com.kilowats.annotations.ValidarVeiculo;
import com.kilowats.entidades.Veiculo;
import com.kilowats.interfaces.IValidacaoCadastro;

public class ServicosVeiculo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject @ValidarVeiculo
	private IValidacaoCadastro validador;
	
	public boolean veiculoIsValido(Veiculo veiculo, String titulo, boolean mostrarMensagem){
		return validador.validarCadastroComMensagem(veiculo, titulo, mostrarMensagem);
	}

}
