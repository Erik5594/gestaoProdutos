package com.kilowats.interfaces;

public interface IPersistirBancoDados {
	
	boolean salvar(Object obj);
	
	boolean alterar(Object obj);
	
	boolean deletar(Object obj);

}