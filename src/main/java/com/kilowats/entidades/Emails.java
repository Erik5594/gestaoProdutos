package com.kilowats.entidades;

import java.io.Serializable;

import lombok.Data;

public @Data class Emails implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long idEmail;
	private Long idPessoa;
	private String emailDestinatario;
	private String nomePessoaDestinatario;

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
