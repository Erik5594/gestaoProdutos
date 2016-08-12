package com.kilowats.entidades;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
public @Data class Empresa extends Pessoa{

	private static final long serialVersionUID = 1L;
	
	private String inscricaoEstadual;
}
