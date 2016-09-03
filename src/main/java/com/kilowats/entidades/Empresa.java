package com.kilowats.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@Entity
public @Data class Empresa extends Pessoa{

	private static final long serialVersionUID = 1L;
	@Column(nullable=true, length=15, name="inscricao_estadual")
	private String inscricaoEstadual;
}
