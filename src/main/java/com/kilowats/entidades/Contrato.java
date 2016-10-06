package com.kilowats.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
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
