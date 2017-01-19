package com.github.erik5594.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="cidade")
public @Data class Cidade implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_cidade")
	private Long idCidade;
	@Column(name="nome_cidade", length=160, nullable=false)
	private String nomeCidade;
	@Column(length=2, nullable=false)
	private String uf;
	@Column(name="cep_inicial", nullable=false)
	private Long cepInicial;
	@Column(name="cep_final", nullable=false)
	private Long cepFinal;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cidade other = (Cidade) obj;
		if (idCidade == null) {
			if (other.idCidade != null)
				return false;
		} else if (!idCidade.equals(other.idCidade))
			return false;
		return true;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idCidade == null) ? 0 : idCidade.hashCode());
		return result;
	}
	
	
}
