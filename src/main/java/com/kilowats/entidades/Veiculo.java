package com.kilowats.entidades;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

import com.kilowats.enuns.TipoVeiculo;

@Entity
@Table(name="veiculo")
public @Data class Veiculo implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable=false, length=7, unique=true)
	private String placa;
	@Column(length=21, unique=true, nullable=false)
	private String chassi;
	@Enumerated(EnumType.ORDINAL)
	@Column(name="tipo_veiculo")
	private TipoVeiculo tipoVeiculo;
	@ManyToOne(cascade = CascadeType.ALL)
	private Cliente cliente;
	@ManyToOne(cascade = CascadeType.ALL)
	private ContratoRastreamentoVeiculo contrado;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chassi == null) ? 0 : chassi.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((placa == null) ? 0 : placa.hashCode());
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
		Veiculo other = (Veiculo) obj;
		if (chassi == null) {
			if (other.chassi != null)
				return false;
		} else if (!chassi.equals(other.chassi))
			return false;
		if (id != other.id)
			return false;
		if (placa == null) {
			if (other.placa != null)
				return false;
		} else if (!placa.equals(other.placa))
			return false;
		return true;
	}
}
