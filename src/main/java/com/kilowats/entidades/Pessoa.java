package com.kilowats.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import lombok.Data;

import org.hibernate.validator.constraints.NotEmpty;

import com.kilowats.enuns.TipoPessoa;

@Entity @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public @Data class Pessoa implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotEmpty
	private String nome;
	@NotEmpty @Column(name="cpf_cgc", length=14, unique=true)
	private String cgcCpf;
	@Column(name="tipo_pessoa", length=1, nullable=false)
	private TipoPessoa fisicaJuridica;//0fisica; 1juridica
	@NotNull @Column(name="status", length=1, nullable=false)
	private int status;//1ativo;0inativo
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_credenciamento", nullable=false, columnDefinition="TIMESTAMP WITH TIME ZONE")
	private Date dataCredenciamento;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_ultima_atualizacao", nullable=false, columnDefinition="TIMESTAMP WITH TIME ZONE")
	private Date ultimaAtualizacao;
}
