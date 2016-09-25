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
import javax.persistence.Transient;

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
	
	@Column(name="endereco_entrega", nullable=false, columnDefinition = "boolean")
	private boolean enderecoEntrega;
	
	@ManyToOne(cascade= CascadeType.ALL)
	@JoinColumn(name="cep")
	private Cep cep;
	
	@Column(name="logradouro", nullable=true, length=150)
	private String rua;
	
	@Column(name="bairro", nullable=true, length=150)
	private String bairro;
	
	@ManyToOne
	@JoinColumn(name="id_pessoa")
	private Pessoa pessoa;
	
	@Transient
	private boolean cepGeral;
	@Transient
	private boolean cepByFaixa;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Endereco other = (Endereco) obj;
		if (bairro == null) {
			if (other.bairro != null)
				return false;
		} else if (!bairro.equals(other.bairro))
			return false;
		if (cep == null) {
			if (other.cep != null)
				return false;
		} else if (!cep.equals(other.cep))
			return false;
		if (complemento == null) {
			if (other.complemento != null)
				return false;
		} else if (!complemento.equals(other.complemento))
			return false;
		if (idComplemento == null) {
			if (other.idComplemento != null)
				return false;
		} else if (!idComplemento.equals(other.idComplemento))
			return false;
		if (rua == null) {
			if (other.rua != null)
				return false;
		} else if (!rua.equals(other.rua))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bairro == null) ? 0 : bairro.hashCode());
		result = prime * result + ((cep == null) ? 0 : cep.hashCode());
		result = prime * result
				+ ((complemento == null) ? 0 : complemento.hashCode());
		result = prime * result
				+ ((idComplemento == null) ? 0 : idComplemento.hashCode());
		result = prime * result + ((rua == null) ? 0 : rua.hashCode());
		return result;
	}
	
	
}
