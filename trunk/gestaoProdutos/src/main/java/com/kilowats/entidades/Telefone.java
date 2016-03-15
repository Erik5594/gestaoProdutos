package com.kilowats.entidades;

import com.kilowats.enuns.TipoTelefoneEnum;

public class Telefone {
	
	private Long idTelefone;
	private Long idPessoa;
	private int ddd;
	private String numero;
	private TipoTelefoneEnum tipoTelefone;
	
	public Long getIdPessoa() {
		return idPessoa;
	}
	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}
	public int getDdd() {
		return ddd;
	}
	public void setDdd(int ddd) {
		this.ddd = ddd;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public Long getIdTelefone() {
		return idTelefone;
	}
	public TipoTelefoneEnum getTipoTelefone() {
		return tipoTelefone;
	}
	public void setTipoTelefone(TipoTelefoneEnum tipoTelefone) {
		this.tipoTelefone = tipoTelefone;
	}
}
