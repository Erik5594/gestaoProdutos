package com.kilowats.entidades;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import lombok.Data;

import com.kilowats.annotations.ProdutoRastreador;

@ProdutoRastreador
@Entity
public @Data class Rastreador implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	private long idRastreador;
	@ManyToOne
	private Empresa fabricante;
	@Transient
	private boolean exigeFabricante;
	@OneToMany(mappedBy="rastreador")
	private List<Chip> chip;
	@OneToOne
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
