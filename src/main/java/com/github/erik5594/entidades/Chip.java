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
import javax.validation.constraints.NotNull;

import lombok.Data;

import com.github.erik5594.annotations.ProdutoChip;
import com.github.erik5594.enuns.Operadora;

@ProdutoChip
@Entity
@Table(name="chip")
public @Data class Chip implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long idChip;
	@NotNull @Column(name="cod_controle", nullable=false)
	private String codControle;
	@NotNull @Column(nullable=false, length=3)
	private int ddd;
	@NotNull @Column(nullable=false, length=10)
	private long numero;
	@NotNull @Column(nullable=false, length=20, unique=true)
	private String imei;
	@NotNull @Enumerated(EnumType.STRING)
	private Operadora operadora;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_rastreador", nullable=true)
	private Rastreador rastreador;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((imei == null) ? 0 : imei.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Chip other = (Chip) obj;
		if (imei == null) {
			if (other.imei != null)
				return false;
		} else if (!imei.equals(other.imei))
			return false;
		return true;
	}

	public boolean isRastreador() {
		return rastreador != null && rastreador.getIdRastreador() != 0L;
	}
}
