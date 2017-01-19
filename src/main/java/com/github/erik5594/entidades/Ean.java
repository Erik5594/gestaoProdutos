package com.github.erik5594.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table(name="ean")
public @Data class Ean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@NotNull @Column(name="cod_barra", length=20, nullable=false)
	private String codBarras;
	@Column(name="ean_tributavel", nullable=false, columnDefinition = "boolean")
	private boolean tributavel;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_produto")
	private Produto produto;
}