package com.kilowats.servicos;

import java.io.Serializable;

import javax.inject.Inject;

import com.kilowats.annotations.ValidarChip;
import com.kilowats.dao.ChipDao;
import com.kilowats.entidades.Chip;
import com.kilowats.interfaces.IValidacaoCadastro;

public class ServicosChip implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ChipDao chipDao;
	@Inject @ValidarChip
	private IValidacaoCadastro validar;
	
	public boolean persistirChip(Chip chip){
		return false;
	}
	
	public boolean chipIsValido(Chip chip, String titulo, boolean mostrarMensagem){
		return validar.validarCadastroComMensagem(chip, titulo, mostrarMensagem);
	}

}
