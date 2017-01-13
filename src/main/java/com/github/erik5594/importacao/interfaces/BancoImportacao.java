package com.github.erik5594.importacao.interfaces;

import java.util.List;

import com.github.erik5594.importacao.enuns.StatusImportacao;


public interface BancoImportacao {
	
	void importar(StatusImportacao status);
	List<?> getClientesImportacao(StatusImportacao status);
	boolean validar(Object obj);
	Object criarObjeto(Object obj) throws Exception;
	void salvarBancoObjetoCriado(Object obj) throws Exception;
	void salvarBancoImportacao(Object obj) throws Exception;

}
