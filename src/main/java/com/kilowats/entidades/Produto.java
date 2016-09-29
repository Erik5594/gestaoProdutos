package com.kilowats.entidades;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
	@NotNull @Min(0) @Max(100) @Column(nullable=false, length=5, precision=2)
	private int quantidade;
	@NotNull @Column(nullable=false, length=8, precision=2)
	private double valor;
	@OneToMany(mappedBy="produto", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Ean> eans;
	
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
