package com.github.erik5594.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import lombok.Data;

import com.github.erik5594.enuns.TipoTelefoneEnum;

@Entity  @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public @Data class Telefone implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id @Column(name="id_telefone") @GeneratedValue(strategy = GenerationType.AUTO)
	private Long idTelefone;
	@Column(length=3, nullable=false)
	private int ddd;
	@Column(length=10, nullable=false)
	private String numero;
	@Enumerated(EnumType.STRING)
	@Column(name="tipo_telefone", nullable=false, length=20)
	private TipoTelefoneEnum tipoTelefone;
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
		if (idTelefone == null) {
			if (other.idTelefone != null)
				return false;
		} else if (!idTelefone.equals(other.idTelefone))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (tipoTelefone != other.tipoTelefone)
			return false;
		return true;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ddd;
		result = prime * result
				+ ((idTelefone == null) ? 0 : idTelefone.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result
				+ ((tipoTelefone == null) ? 0 : tipoTelefone.hashCode());
		return result;
	}
	
	
}
