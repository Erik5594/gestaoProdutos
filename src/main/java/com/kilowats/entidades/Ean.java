package com.kilowats.entidades;

import java.io.Serializable;

import lombok.Data;

public @Data class Ean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private long id;
	private String codBarras;
}
