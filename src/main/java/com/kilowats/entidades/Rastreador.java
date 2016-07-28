package com.kilowats.entidades;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.kilowats.annotations.ProdutoRastreador;

@ProdutoRastreador
@EqualsAndHashCode(callSuper=false)
public @Data class Rastreador extends Produto{

	private static final long serialVersionUID = 1L;
	
	private long idRastreador;
	private Empresa fabricante;
	private boolean exigeFabricante;
	private Chip chip;
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
