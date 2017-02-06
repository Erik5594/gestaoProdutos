package com.github.erik5594.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.Data;

import com.github.erik5594.enuns.FormaPagamento;
import com.github.erik5594.enuns.StatusOrdemServico;
import com.github.erik5594.util.Utils;

@Entity
@Table(name="ordem_servico")
public @Data class OrdemServico implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_cliente", nullable=false)
	private Cliente cliente;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_veiculo", nullable=true)
	private Veiculo veiculo;
	
	@OneToMany(mappedBy="ordemServico",fetch=FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval = true)
	private List<ItemOrdemServico> itens = new ArrayList<>();
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_ordem_servico", nullable=false, columnDefinition="TIMESTAMP WITH TIME ZONE")
	private Date dataOrdemServico;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_agendado", nullable=false, columnDefinition="TIMESTAMP WITH TIME ZONE")
	private Date dataAgendado;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_executado", nullable=true, columnDefinition="TIMESTAMP WITH TIME ZONE")
	private Date dataExecutado;
	
	@Enumerated(EnumType.STRING) @Column(name="forma_pagamento")
	private FormaPagamento formaPagamento;
	
	@Enumerated(EnumType.STRING) @Column(name="status_ordem_servico", nullable=false)
	private StatusOrdemServico statusOrdemServico;
	
	@Column(name="observacao", nullable=true, length=150)
	private String observacao;
	
	@Column(length=10, scale=2, nullable=false, name="valor_total_desconto")
	private BigDecimal valorTotalDesconto = BigDecimal.ZERO;
	
	@Column(length=10, scale=2, nullable=false, name="valor_total")
	private BigDecimal valorTotal = BigDecimal.ZERO;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_contrato", nullable=true)
	private Contrato contrato;
	
	@Override @Transient
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrdemServico other = (OrdemServico) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override @Transient
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Transient
	public boolean isFinalizado() {
		return StatusOrdemServico.FINALIZADO.equals(this.getStatusOrdemServico());
	}
	
	@Transient
	public BigDecimal getValorSubtotal() {
		return this.getValorTotal().add(this.getValorTotalDesconto());
	}
	
	@Transient
	public void recalcularValorTotal() {
		BigDecimal total = BigDecimal.ZERO;
		
		total = total.subtract(this.getValorTotalDesconto());
		
		for (ItemOrdemServico item : this.getItens()) {
			if (item.getProduto() != null && item.getProduto().getId() != null) {
				total = total.add(item.getValorTotal());
			}
		}
		
		this.setValorTotal(total);
	}
	
	@Transient
	public void recalcularValorTotalDesconto() {
		BigDecimal total = BigDecimal.ZERO;
		
		//total = total.subtract(this.getValorTotalDesconto());
		
		for (ItemOrdemServico item : this.getItens()) {
			if (item.getProduto() != null && item.getProduto().getId() != null) {
				total = total.add(item.getValorTotalDesconto());
			}
		}
		
		this.setValorTotalDesconto(total);
	}
	
	@Transient
	public void adicionarItemVazio() {
		Produto produto = new Produto();
		
		ItemOrdemServico item = new ItemOrdemServico();
		item.setProduto(produto);
		item.setOrdemServico(this);
		
		this.getItens().add(0, item);
	}
	
	@Transient
	public void removerItemVazio() {
		ItemOrdemServico primeiroItem = this.getItens().get(0);
		
		if (primeiroItem != null && primeiroItem.getProduto().getId() == null) {
			this.getItens().remove(0);
		}
	}
	
	@Transient
	public boolean temItemVazio() {
		if(this.getItens() == null || this.getItens().isEmpty()){
			return false;
		}
		ItemOrdemServico primeiroItem = this.getItens().get(0);
		
		if (primeiroItem != null && primeiroItem.getProduto().getId() == null) {
			return true;
		}
		return false;
	}
	
	@Transient
	public void removerItem(ItemOrdemServico itemASerRemovido) {
		if(Utils.isNotNullOrEmpty(this.getItens()) && this.itens.contains(itemASerRemovido)){
			this.itens.remove(itemASerRemovido);
		}
	}
}
