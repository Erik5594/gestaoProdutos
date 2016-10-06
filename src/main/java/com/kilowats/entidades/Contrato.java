package com.kilowats.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

public @Data class Contrato implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;
	@ManyToOne(cascade=CascadeType.ALL)
	private Cliente cliente;
	@Column(name="meses_fidelidade", length=3, precision=0)
	private int mesesFidelidade;
	@Temporal(TemporalType.DATE) @Column(name="data_inicio", nullable=false)
	private Date dataInicio;
	@Temporal(TemporalType.DATE) @Column(name="data_fim", nullable=false)
	private Date dataFim;
}
