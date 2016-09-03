package com.kilowats.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
public @Data class Cep implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long cep;
	@NotNull @Column(name="logradouro", nullable=false, length=150)
	private String rua;
	@NotNull @Column(name="bairro", nullable=false, length=150)
	private String bairro;
	@ManyToOne
	private Cidade cidade;
}
