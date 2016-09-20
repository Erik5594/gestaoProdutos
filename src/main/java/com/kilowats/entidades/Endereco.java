package com.kilowats.entidades;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
public @Data class Endereco implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(name="id_complemento")
	private Long idComplemento;
	@Column(nullable=false, length=100)
	private String complemento;
	@Column(name="numero_endereco", nullable=false, length=10)
	private String numero;
	@Column(name="endereco_entrega", nullable=false)
	private boolean enderecoEntrega;
	@ManyToOne(cascade= CascadeType.ALL)
	@JoinColumn(name="id_pessoa")
	private Pessoa pessoa;
	@ManyToOne(cascade= CascadeType.ALL)
	@JoinColumn(name="cep")
	private Cep cep;
}
