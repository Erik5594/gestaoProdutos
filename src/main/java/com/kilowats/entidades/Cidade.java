package com.kilowats.entidades;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table(name="tbl_cidade")
public @Data class Cidade implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_cidade")
	private Long idCidade;
	@NotNull
	@Column(name="nome_cidade")
	private String nomeCidade;
	@NotNull
	@Column(name="sigla_cidade")
	private String siglaCidade;
	@Column(length=2, nullable=false) @NotNull
	private String uf;
	@OneToMany(mappedBy="cidade")
	private List<Cep> cep;
}
