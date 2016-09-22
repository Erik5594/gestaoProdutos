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

import com.kilowats.util.Utils;

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
	@Column(name="logradouro", nullable=true, length=150)
	private String rua;
	@Column(name="bairro", nullable=true, length=150)
	private String bairro;
	
	public boolean isCepGeral(){
		if(Utils.isNotNullOrEmpty(cep) && cep.getCidade().getCepInicial() == cep.getCidade().getCepFinal()){
			return true;
		}
		return false;
	}
	
	public boolean isCepByFaixa(){
		if(Utils.isNotNullOrEmpty(cep) && Utils.isNullOrEmpty(cep.getRua())
				&& cep.getCep() <= cep.getCidade().getCepFinal()
				&& cep.getCep() >= cep.getCidade().getCepInicial()){
			return true;
		}
		return false;
	}
}
