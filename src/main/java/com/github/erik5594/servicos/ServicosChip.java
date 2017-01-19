package com.github.erik5594.servicos;

import java.io.Serializable;

import javax.inject.Inject;

import com.github.erik5594.annotations.ValidarChip;
import com.github.erik5594.dao.ChipDao;
import com.github.erik5594.entidades.Chip;
import com.github.erik5594.interfaces.IValidacaoCadastro;

public class ServicosChip implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ChipDao chipDao;
	@Inject @ValidarChip
	private IValidacaoCadastro validar;
	
	public Chip persistirChip(Chip chip){
		return chipDao.salvarOrUpdate(chip);
	}
	
	public boolean chipIsValido(Chip chip, String titulo, boolean mostrarMensagem){
		return validar.validarCadastroComMensagem(chip, titulo, mostrarMensagem);
	}

}
