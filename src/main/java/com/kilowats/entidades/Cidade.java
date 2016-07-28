package com.kilowats.entidades;

import java.io.Serializable;

import lombok.Data;

public @Data class Cidade implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long idCidade;
	private String nomeCidade;
	private String siglaCidade;
	private String uf;
}
