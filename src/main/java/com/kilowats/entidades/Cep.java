package com.kilowats.entidades;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
public @Data class Cep implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public Cep(){
	}
	
	public Cep(Long cep){
		this.cep = cep;
	}
	
	@NotNull @Id
	private Long cep;
	@Column(name="logradouro", nullable=true, length=150)
	private String rua;
	@Column(name="bairro", nullable=true, length=150)
	private String bairro;
	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="id_cidade", nullable=false)
	private Cidade cidade;
}
