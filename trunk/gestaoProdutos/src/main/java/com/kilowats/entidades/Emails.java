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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((emailDestinatario == null) ? 0 : emailDestinatario
						.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Emails other = (Emails) obj;
		if (emailDestinatario == null) {
			if (other.emailDestinatario != null)
				return false;
		} else if (!emailDestinatario.equals(other.emailDestinatario))
			return false;
		return true;
	}
	
}
