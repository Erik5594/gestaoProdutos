package com.kilowats.entidades;

import java.io.Serializable;

import lombok.Data;

import com.kilowats.enuns.TipoTelefoneEnum;

public @Data class Telefone implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long idTelefone;
	private Long idPessoa;
	private int ddd;
	private String numero;
	private TipoTelefoneEnum tipoTelefone;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ddd;
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Telefone other = (Telefone) obj;
		if (ddd != other.ddd)
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}
}
