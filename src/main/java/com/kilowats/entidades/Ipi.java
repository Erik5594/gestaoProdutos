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
@Table(name="ipi")
public @Data class Ipi implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(name="classe_enquadramento", length=5, nullable=false)
	private String classeEnquadramento;
	@Column(name="cod_enquadramento_legal", length=5, nullable=false)
	private String codEnquadramentoLegal;
	@Column(name="cnpj_produto", length=14, nullable=false)
	private String cnpjProduto;
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_produto")
	private Produto produto;
}
