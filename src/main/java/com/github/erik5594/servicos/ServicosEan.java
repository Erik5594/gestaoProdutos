package com.github.erik5594.servicos;

import java.io.Serializable;

import javax.inject.Inject;

import com.github.erik5594.annotations.ValidarEan;
import com.github.erik5594.dao.EanDao;
import com.github.erik5594.entidades.Ean;
import com.github.erik5594.interfaces.IValidacaoCadastro;

public class ServicosEan implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EanDao eanDao;
	@Inject @ValidarEan
	private IValidacaoCadastro validador;
	
	public boolean eanIsValido(Ean ean, String titulo, boolean mostrarMensagem){
		return validador.validarCadastroComMensagem(ean, titulo, mostrarMensagem);
	}
	
	public Ean pesquisarEanByCodBarras(String codBarras){
		return eanDao.pesquisarEanByCodBarras(codBarras);
	}

}
