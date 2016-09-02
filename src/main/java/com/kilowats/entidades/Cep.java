package com.kilowats.entidades;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table(name="tbl_cep")
public @Data class Cep implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long cep;
	@NotNull
	private String rua;
	private String bairro;
	@ManyToOne
	private Cidade cidade;
}
