package com.github.erik5594.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Table(name="contrato")
public @Data class Contrato implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="id_cliente", nullable=false)
	private Cliente cliente;
	@Column(name="meses_fidelidade", length=3, precision=0)
	private int mesesFidelidade;
	@Temporal(TemporalType.DATE) @Column(name="data_inicio", nullable=false)
	private Date dataInicio;
	@Temporal(TemporalType.DATE) @Column(name="data_fim", nullable=false)
	private Date dataFim;
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="contrato")
	private List<OrdemServico> ordemsServicos;
}
