package com.kilowats.entidades;

import java.io.Serializable;
import java.util.List;

import com.kilowats.enuns.TipoPessoa;

public class Pessoa implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long idPessoa;
	private String nome;
	private String cgcCpf;
	private TipoPessoa fisicaJuridica;//0fisica; 1juridica
	private Endereco endereco;
	private List<Telefone> telefones;
	private List<Emails> emails;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCgcCpf() {
		return cgcCpf;
	}
	public void setCgcCpf(String cgcCpf) {
		this.cgcCpf = cgcCpf;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	public List<Telefone> getTelefones() {
		return telefones;
	}
	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}
	public List<Emails> getEmails() {
		return emails;
	}
	public void setEmails(List<Emails> emails) {
		this.emails = emails;
	}
	public Long getIdPessoa() {
		return idPessoa;
	}
	public TipoPessoa getFisicaJuridica() {
		return fisicaJuridica;
	}
	public void setFisicaJuridica(TipoPessoa fisicaJuridica) {
		this.fisicaJuridica = fisicaJuridica;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj != null){
		Pessoa pessoa = (Pessoa) obj;
		if(pessoa.getIdPessoa() == this.idPessoa){
			return true;
		}
		}
		return false;
	}
	
}
