package com.kilowats.entidades;

import java.io.Serializable;

import lombok.Data;

public @Data class Endereco implements Serializable{

	private static final long serialVersionUID = 1L;

	private String complemento;
	private String numero;
	private Cep cep;
}
