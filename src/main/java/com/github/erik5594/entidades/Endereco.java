package com.github.erik5594.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.github.erik5594.util.Utils;

import lombok.Data;

@Entity @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
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
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="cep")
	private Cep cep;
	
	@Column(name="logradouro", nullable=true, length=150)
	private String rua;
	
	@Column(name="bairro", nullable=true, length=150)
	private String bairro;
	
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
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cep == null) ? 0 : cep.hashCode());
		result = prime * result
				+ ((complemento == null) ? 0 : complemento.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		return result;
	}
	
	@Transient
	public void ajustarEndereco(){
		if(Utils.isNotNullOrEmpty(this) && Utils.isNotNullOrEmpty(this.cep)){
			this.rua = this.cep.getRua();
			this.bairro = this.cep.getBairro();
		}
	}
}
