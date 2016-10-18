package com.kilowats.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.Data;

import com.kilowats.enuns.FormaPagamento;
import com.kilowats.enuns.StatusOrdemServico;

@Entity
@Table(name="ordem_servico")
public @Data class OrdemServico implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="id_cliente", nullable=false)
	private Cliente cliente;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="id_veiculo", nullable=false)
	private Veiculo veiculo;
	
	@OneToMany(mappedBy="ordemServico",fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	private List<ItensOrdemServico> itens;
	
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
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="id_contrato", nullable=true)
	private Contrato contrato;
	
	@Override
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Transient
	public boolean isFinalizado() {
		return StatusOrdemServico.CONCLUIDO.equals(this.getStatusOrdemServico());
	}
	
	@Transient
	public BigDecimal getValorSubtotal() {
		return this.getValorTotal().add(this.getValorTotalDesconto());
	}
	
}
