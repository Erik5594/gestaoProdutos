package com.kilowats.entidades;

public class Empresa extends Pessoa{

	private static final long serialVersionUID = 1L;
	
	private String inscricaoEstadual;

	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}
}
