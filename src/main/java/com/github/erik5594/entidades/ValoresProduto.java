package com.github.erik5594.entidades;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table(name="valor_produto")
public @Data class ValoresProduto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@NotNull @Column(nullable=false, precision=10, scale=2, name="valor_comercial")
	private BigDecimal valorComercial = BigDecimal.ZERO;
	@Column(precision=10, scale=2, name="valor_custo")
	private BigDecimal valorCusto = BigDecimal.ZERO;
	@Column(precision=10, scale=2, name="valor_tributavel")
	private BigDecimal valorTributavel = BigDecimal.ZERO;
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_produto")
	private Produto produto;

}
