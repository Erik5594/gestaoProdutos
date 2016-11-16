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

import com.kilowats.enuns.SituacaoTributariaIcms;
import com.kilowats.enuns.TipoRegimeFiscal;
import com.kilowats.enuns.TributacaoOrigemProduto;

@Entity
@Table(name="icms")
public class Icms implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@NotNull @Enumerated(EnumType.ORDINAL) @Column(name="tipo_regime_fiscal")
	private TipoRegimeFiscal tipoRegimeFiscal;
	@NotNull @Enumerated(EnumType.ORDINAL) @Column(name="situacao_trubutaria_icms")
	private SituacaoTributariaIcms situacaoTributariaIcms;
	@NotNull @Column(nullable=false, precision=10, scale=2, name="aliquota_aplicavel_calculo_credito")
	private BigDecimal aliquotaAplicavelCalculoCredito = BigDecimal.ZERO;
	@NotNull @Enumerated(EnumType.ORDINAL) @Column(name="origem_tributavel")
	private TributacaoOrigemProduto origemTributavel;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_produto")
	private Produto produto;

}
