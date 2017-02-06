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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

import com.github.erik5594.enuns.TipoProdutoUnidadeEnum;

@Entity
@Table(name="itens_ordem_servico")
public @Data class ItemOrdemServico implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_produto", nullable=false)
	private Produto produto;
	
	@Column(length=10, precision=2, nullable=false, name="valor_desconto")
	private BigDecimal valorDesconto = BigDecimal.ZERO;
	
	@Column(length=5, precision=3, nullable=false, name="porcentual_desconto", columnDefinition="numeric(5,3) default 0")
	private BigDecimal porcentualDesconto = BigDecimal.ZERO;
	
	@Column(nullable=false, name="quantidade_produto")
	private BigDecimal quantidadeProduto = BigDecimal.ONE;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_ordem_servico", nullable=false)
	private OrdemServico ordemServico;
	
	@Column(name = "valor_unitario", nullable = false, precision = 10, scale = 2)
	private BigDecimal valorUnitario = BigDecimal.ZERO;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemOrdemServico other = (ItemOrdemServico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id)){
			return false;
		}
		if (produto == null){
			if (other.getProduto() != null)
				return false;
		} else if (!produto.equals(other.getProduto()))
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
	
	@Transient
	public BigDecimal getValorTotal() {
		return this.getValorUnitario().multiply(this.getQuantidadeProduto());
	}
	
	@Transient
	public BigDecimal getValorTotalDesconto() {
		return this.getValorDesconto().multiply(this.getQuantidadeProduto());
	}
	
	@Transient
	public boolean isProdutoAssociado() {
		return this.getProduto() != null && this.getProduto().getId() != null;
	}
	
	@Transient
	public boolean isEstoqueSuficiente() {
		return (this.getOrdemServico() != null && this.getOrdemServico().isFinalizado()) || this.getProduto() == null
				|| this.getProduto().getId() == null || this.getProduto().getTipoUnidade() == TipoProdutoUnidadeEnum.SV
				|| this.getQuantidadeProduto().compareTo(this.getProduto().getEstoqueProduto().getQuantidadeEstoque()) < 0; 
	}
	
	@Transient
	public boolean isEstoqueInsuficiente() {
		return !this.isEstoqueSuficiente();
	}
}
