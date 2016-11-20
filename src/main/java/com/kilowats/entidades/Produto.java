package com.kilowats.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import lombok.Data;

import com.kilowats.enuns.TipoProdutoUnidadeEnum;

@Entity
@Table(name="produto")
public @Data class Produto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull @Column(name="cod_produto", length=15, nullable=false, unique=true)
	private String codProduto;
	@NotNull @Column(name="nome_produto", length=120, nullable=false)
	private String nomeProduto;
	@NotNull @Enumerated(EnumType.ORDINAL) @Column(name="tipo_unidade")
	private TipoProdutoUnidadeEnum tipoUnidade;
	@NotNull @Enumerated(EnumType.ORDINAL) @Column(name="tipo_unidade_tributavel")
	private TipoProdutoUnidadeEnum tipoUnidadeTributavel;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_ultima_atualizacao", nullable=false, columnDefinition="TIMESTAMP WITH TIME ZONE")
	private Date dataUltimaAtualizacao;
	@OneToMany(mappedBy="produto", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Ean> eans;
	@OneToOne(mappedBy = "produto", cascade=CascadeType.REMOVE, fetch=FetchType.EAGER)
	private ValoresProduto valoresProdutos;
	@OneToOne(mappedBy = "produto", cascade=CascadeType.REMOVE, fetch=FetchType.EAGER)
	private EstoqueProduto estoqueProduto;
	@OneToOne(mappedBy = "produto", cascade=CascadeType.REMOVE, fetch=FetchType.EAGER)
	private Tipi tipi;
	@OneToOne(mappedBy = "produto", cascade=CascadeType.REMOVE, fetch=FetchType.EAGER)
	private Icms icms;
	@OneToOne(mappedBy = "produto", cascade=CascadeType.REMOVE, fetch=FetchType.EAGER)
	private Ipi ipi;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
