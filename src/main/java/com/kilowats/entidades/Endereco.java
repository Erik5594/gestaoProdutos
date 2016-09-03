package com.kilowats.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
public @Data class Endereco implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id @Column(name="id_complemento")
	private Long idComplemento;
	@NotNull @Column(nullable=false, length=100)
	private String complemento;
	@NotNull @Column(name="numero_endereco", nullable=false, length=10)
	private String numero;
	@ManyToOne
	@JoinColumn(name="cep")
	private Cep cep;
}
