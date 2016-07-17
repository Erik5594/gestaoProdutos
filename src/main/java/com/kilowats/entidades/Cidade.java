package com.kilowats.entidades;

import java.io.Serializable;

public class Cidade implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long idCidade;
	private String nomeCidade;
	private String siglaCidade;
	private String uf;
	
	public String getNomeCidade() {
		return nomeCidade;
	}
	public void setNomeCidade(String nomeCidade) {
		this.nomeCidade = nomeCidade;
	}
	public String getSiglaCidade() {
		return siglaCidade;
	}
	public void setSiglaCidade(String siglaCidade) {
		this.siglaCidade = siglaCidade;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public Long getIdCidade() {
		return idCidade;
	}
	
}
