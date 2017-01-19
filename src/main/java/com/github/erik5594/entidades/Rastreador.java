package com.github.erik5594.entidades;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import lombok.Data;

import com.github.erik5594.annotations.ProdutoRastreador;

@ProdutoRastreador
@Entity
public @Data class Rastreador implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	private long idRastreador;
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="id_fabricante", nullable=true)
	private Fornecedor fabricante;
	@Transient
	private boolean exigeFabricante;
	@OneToMany(mappedBy="rastreador", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<Chip> chip;
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="id_veiculo", nullable=true)
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
