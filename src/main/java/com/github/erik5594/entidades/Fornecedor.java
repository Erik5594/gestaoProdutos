package com.github.erik5594.entidades;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@Entity
public @Data class Fornecedor extends Pessoa{

	private static final long serialVersionUID = 1L;
	@Column(nullable=true, length=15, name="inscricao_estadual")
	private String inscricaoEstadual;
	@OneToMany(mappedBy="fornecedor", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<EnderecoFornecedor> endereco;
	@OneToMany(mappedBy="fornecedor", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<TelefoneFornecedor> telefones;
	@OneToMany(mappedBy="fornecedor", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<EmailFornecedor> emails;
}
