package com.kilowats.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="tipi")
public @Data class Tipi implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(name="cod_exec_tipi", nullable=false, length=15)
	private String codExecaoTipi;
	@Column(name="cod_genero_tipi", nullable=false, length=15)
	private String codGenero;
	@Column(name="ncm_produto", nullable=false, length=15)
	private String ncm;
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_produto")
	private Produto produto;
}
