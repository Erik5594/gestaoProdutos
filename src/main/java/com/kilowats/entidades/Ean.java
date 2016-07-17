package com.kilowats.entidades;

import java.io.Serializable;

public class Ean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private long id;
	private String codBarras;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCodBarras() {
		return codBarras;
	}
	public void setCodBarras(String codBarras) {
		this.codBarras = codBarras;
	}
}
