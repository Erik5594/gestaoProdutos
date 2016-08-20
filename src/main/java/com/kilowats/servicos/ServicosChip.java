package com.kilowats.servicos;

import java.io.Serializable;

import javax.inject.Inject;

import com.kilowats.annotations.CadastrarChip;
import com.kilowats.annotations.ValidarChip;
import com.kilowats.entidades.Chip;
import com.kilowats.interfaces.IPersistirBancoDados;
import com.kilowats.interfaces.IValidacaoCadastro;

public class ServicosChip implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject @CadastrarChip
	private IPersistirBancoDados persistir;
	@Inject @ValidarChip
	private IValidacaoCadastro validar;
	
	public boolean persistirChip(Chip chip){
		return false;
	}
	
	public boolean chipIsValido(Chip chip, String titulo){
		return validar.validarCadastroComMensagem(chip, titulo);
	}

}
