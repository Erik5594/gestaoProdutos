package com.github.erik5594.exceptions;

public class NegocioException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public NegocioException(String mensagem){
		super(mensagem);
	}
}
