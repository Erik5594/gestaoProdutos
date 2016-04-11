package com.kilowats.entidades;

import com.kilowats.enuns.Operadora;

public class Chip extends Produto{
	
	private Long idChip;
	private Operadora operadora;
	private int ddd;
	private long numero;
	private String imei;
	private Rastreador restreador;
	
	public Long getIdChip() {
		return idChip;
	}
	public void setIdChip(Long idChip) {
		this.idChip = idChip;
	}
	public Operadora getOperadora() {
		return operadora;
	}
	public void setOperadora(Operadora operadora) {
		this.operadora = operadora;
	}
	public int getDdd() {
		return ddd;
	}
	public void setDdd(int ddd) {
		this.ddd = ddd;
	}
	public long getNumero() {
		return numero;
	}
	public void setNumero(long numero) {
		this.numero = numero;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	
	public Rastreador getRestreador() {
		return restreador;
	}
	public void setRestreador(Rastreador restreador) {
		this.restreador = restreador;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((imei == null) ? 0 : imei.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Chip other = (Chip) obj;
		if (imei == null) {
			if (other.imei != null)
				return false;
		} else if (!imei.equals(other.imei))
			return false;
		return true;
	}
	
	
}