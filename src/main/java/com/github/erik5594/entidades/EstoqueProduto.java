package com.github.erik5594.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table(name="estoque_produto")
public @Data class EstoqueProduto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@NotNull @Min(0) @Max(999999)
	@Column(name="quantidade_estoque",nullable=false, precision=8, scale = 2)
	private BigDecimal quantidadeEstoque = new BigDecimal(BigInteger.ONE);
	@Min(0) @Max(999999)
	@Column(name="quantidade_pendente_entrada", nullable=false, precision=8, scale = 2)
	private BigDecimal quantidadePendenteEntrada = new BigDecimal(BigInteger.ZERO);
	@Min(0) @Max(999999)
	@Column(name="quantidade_pendente_saida", nullable=false, precision=8, scale = 2)
	private BigDecimal quantidadePendenteSaida = new BigDecimal(BigInteger.ZERO);
	@Min(0) @Max(999999)
	@Column(name="quantidade_tributavel", nullable=false, precision=8, scale = 2)
	private BigDecimal quantidadeTributavel = new BigDecimal(BigInteger.ONE);
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_produto")
	private Produto produto;

}
