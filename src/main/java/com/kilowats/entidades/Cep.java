package com.kilowats.entidades;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public @Data class Cep implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@NotNull @Id
	private Long cep;
	@NotEmpty @Column(name="logradouro", nullable=false, length=150)
	private String rua;
	@NotEmpty @Column(name="bairro", nullable=false, length=150)
	private String bairro;
	@NotNull @ManyToOne(cascade = CascadeType.ALL)
	private Cidade cidade;
}
