package com.kilowats.entidades;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

import com.kilowats.enuns.SituacaoTributariaIcms;
import com.kilowats.enuns.TipoRegimeFiscal;
import com.kilowats.enuns.TributacaoOrigemProduto;

@Entity
@Table(name="icms")
public @Data class Icms implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Enumerated(EnumType.ORDINAL) @Column(name="tipo_regime_fiscal")
	private TipoRegimeFiscal tipoRegimeFiscal;
	@Enumerated(EnumType.ORDINAL) @Column(name="situacao_trubutaria_icms")
	private SituacaoTributariaIcms situacaoTributariaIcms;
	@Column(nullable=false, precision=10, scale=2, name="aliquota_aplicavel_calculo_credito")
	private BigDecimal aliquotaAplicavelCalculoCredito = BigDecimal.ZERO;
	@Enumerated(EnumType.ORDINAL) @Column(name="origem_tributavel")
	private TributacaoOrigemProduto origemTributavel;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_produto")
	private Produto produto;

}
