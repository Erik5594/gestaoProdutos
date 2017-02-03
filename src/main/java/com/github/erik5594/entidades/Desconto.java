package com.github.erik5594.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table(name="descontos")
public @Data class Desconto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull @Column(length=30, nullable=false, name="nome", unique=true)
	private String nome;
	
	@Column(name="data_inicio_virgencia", nullable=false)
	@NotNull @Temporal(TemporalType.DATE)
	private Date dataInicio;
	
	@Column(name="data_fim_virgencia", nullable=false)
	@NotNull @Temporal(TemporalType.DATE)
	private Date dataFim;
	
	@Column(name="ativo", nullable=false, columnDefinition="boolean")
	private boolean ativo;
	
	@NotNull @Column(nullable=false, precision=6, scale=3, name="perc_min")
	private BigDecimal PercentualMinimoDesconto;
	
	@NotNull @Column(nullable=false, precision=6, scale=3, name="perc_max")
	private BigDecimal PercentualMaximoDesconto;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinTable(name = "desconto_produto", joinColumns = @JoinColumn(name="desconto_id"),
			inverseJoinColumns = @JoinColumn(name = "produto_id"),
			foreignKey = @ForeignKey(name = "fk_desconto_id"),
			inverseForeignKey = @ForeignKey(name = "fk_produto_id"))
	private List<Produto> produtos;
	
	@Column(name="data_criacao", nullable=false)
	@NotNull @Temporal(TemporalType.DATE)
	private Date dataCriacao = new Date();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Desconto other = (Desconto) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return id.toString();
	}
	
}
