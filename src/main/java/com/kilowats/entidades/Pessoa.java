package com.kilowats.entidades;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

import com.kilowats.enuns.TipoPessoa;

public @Data class Pessoa implements Serializable{

	private static final long serialVersionUID = 1L;

	private String nome;
	private String cgcCpf;
	private TipoPessoa fisicaJuridica;//0fisica; 1juridica
	private Endereco endereco;
	private List<Telefone> telefones;
	private List<Emails> emails;
}
