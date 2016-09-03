package com.kilowats.entidades;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

import com.kilowats.annotations.ProdutoRastreador;

@ProdutoRastreador
public @Data class Rastreador implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private long idRastreador;
	private Empresa fabricante;
	private boolean exigeFabricante;
	private List<Chip> chip;
	private Veiculo veiculo;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rastreador other = (Rastreador) obj;
		if (idRastreador != other.idRastreador)
			return false;
		return true;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (idRastreador ^ (idRastreador >>> 32));
		return result;
	}
	
	
}
