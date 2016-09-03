package com.kilowats.entidades;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
public @Data class Cidade implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_cidade")
	private Long idCidade;
	@NotNull
	@Column(name="nome_cidade", length=160, nullable=false)
	private String nomeCidade;
	@NotNull
	@Column(name="sigla_cidade", length=160, nullable=false)
	private String siglaCidade;
	@Column(length=2, nullable=false) @NotNull
	private String uf;
	@OneToMany(mappedBy="cidade")
	private List<Cep> cep;
}
