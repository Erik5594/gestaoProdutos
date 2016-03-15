package com.kilowats.entidades;

public class Emails {

	private Long idEmail;
	private Long idPessoa;
	private String emailDestinatario;
	private String nomePessoaDestinatario;
	
	public Long getIdPessoa() {
		return idPessoa;
	}
	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}
	public String getEmailDestinatario() {
		return emailDestinatario;
	}
	public void setEmailDestinatario(String emailDestinatario) {
		this.emailDestinatario = emailDestinatario;
	}
	public String getNomePessoaDestinatario() {
		return nomePessoaDestinatario;
	}
	public void setNomePessoaDestinatario(String nomePessoaDestinatario) {
		this.nomePessoaDestinatario = nomePessoaDestinatario;
	}
	public Long getIdEmail() {
		return idEmail;
	}
}
