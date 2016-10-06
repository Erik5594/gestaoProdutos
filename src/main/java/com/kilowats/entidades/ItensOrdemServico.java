package com.kilowats.entidades;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="ordem_servico")
public @Data class ItensOrdemServico implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="id_produto", nullable=false)
	private Produto produto;
	@Column(length=10, precision=2, nullable=false, name="valor_desconto")
	private BigDecimal valorDesconto = BigDecimal.ZERO;
	@Column(nullable=false, name="quantidade_produto")
	private Integer quantidadeProduto = 1;
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="id_ordem_servico", nullable=false)
	private OrdemServico ordemServico;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItensOrdemServico other = (ItensOrdemServico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
}
