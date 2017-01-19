package com.github.erik5594.servicos;

import java.io.Serializable;

import javax.inject.Inject;

import com.github.erik5594.annotations.ValidarVeiculo;
import com.github.erik5594.entidades.Veiculo;
import com.github.erik5594.interfaces.IValidacaoCadastro;

public class ServicosVeiculo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject @ValidarVeiculo
	private IValidacaoCadastro validador;
	
	public boolean veiculoIsValido(Veiculo veiculo, String titulo, boolean mostrarMensagem){
		return validador.validarCadastroComMensagem(veiculo, titulo, mostrarMensagem);
	}

}
