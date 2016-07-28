package com.kilowats.entidades;

import java.io.Serializable;

import lombok.Data;

public @Data class Cep implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int cep;
	private String rua;
	private String bairro;
	private Cidade cidade;
}
