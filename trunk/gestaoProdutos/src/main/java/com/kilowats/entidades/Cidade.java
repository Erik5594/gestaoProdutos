package com.kilowats.entidades;

public class Cidade {

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
