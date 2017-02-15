package com.github.erik5594.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

import com.github.erik5594.enuns.TipoVeiculo;

@Entity
@Table(name="veiculo")
public @Data class Veiculo implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable=false, length=7, unique=true)
	private String placa;
	@Column(length=21, unique=false, nullable=true)
	private String chassi;
	@Enumerated(EnumType.ORDINAL)
	@Column(name="tipo_veiculo")
	private TipoVeiculo tipoVeiculo;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_cliente", nullable=false)
	private Cliente cliente;
	@Column(nullable=true, length=30)
	private String marca;
	@Column(nullable=true, length=50)
	private String modelo;
	@Column(nullable=true, length=15)
	private String cor;
	@Column(nullable=true, length=4)
	private Integer ano;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Veiculo other = (Veiculo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (placa == null) {
			if (other.placa != null)
				return false;
		} else if (!placa.equals(other.placa))
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((placa == null) ? 0 : placa.hashCode());
		return result;
	}
	
}
