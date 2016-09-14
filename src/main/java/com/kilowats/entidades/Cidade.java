package com.kilowats.entidades;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.Data;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public @Data class Cidade implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_cidade")
	private Long idCidade;
	@NotEmpty
	@Column(name="nome_cidade", length=160, nullable=false, unique=true)
	private String nomeCidade;
	@NotEmpty
	@Column(name="sigla_cidade", length=160, nullable=false, unique=true)
	private String siglaCidade;
	@NotEmpty @Column(length=2, nullable=false)
	private String uf;
	@OneToMany(mappedBy="cidade")
	private List<Cep> cep;
	@NotNull @Column(name="cep_inicial", nullable=false)
	private Long cepInicial;
	@NotNull @Column(name="cep_final", nullable=false)
	private Long cepFinal;
}
