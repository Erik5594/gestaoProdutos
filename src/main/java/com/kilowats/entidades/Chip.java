package com.kilowats.entidades;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import lombok.Data;

import com.kilowats.annotations.ProdutoChip;
import com.kilowats.enuns.Operadora;

@ProdutoChip
public @Data class Chip implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long idChip;
	private String codControle;
	private int ddd;
	private long numero;
	private String imei;
	private Operadora operadora;
	private Rastreador rastreador;
	
	public Chip(){
		super();
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

	public boolean isRastreador() {
		return rastreador != null && rastreador.getIdRastreador() != 0L;
	}
}
