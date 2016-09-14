package com.kilowats.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

import com.kilowats.enuns.TipoTelefoneEnum;
@Entity
@Table(name="telefone")
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
	@ManyToOne
	@JoinColumn(name="id_pessoa")
	private Cliente cliente;
	
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
