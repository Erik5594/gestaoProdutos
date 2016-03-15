package com.kilowats.entidades;

import java.util.List;

public class Pessoa {

	private Long idPessoa;
	private String nome;
	private String cgcCpf;
	private int fisicaJuridica;//0fisica; 1juridica
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
	public int getFisicaJuridica() {
		return fisicaJuridica;
	}
	public void setFisicaJuridica(int fisicaJuridica) {
		this.fisicaJuridica = fisicaJuridica;
	}
	
}
