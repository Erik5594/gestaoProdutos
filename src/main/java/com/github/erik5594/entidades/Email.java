package com.github.erik5594.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import lombok.Data;

@Entity @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public @Data class Email implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(name="id_email")
	private Long idEmail;
	@Column(name="email_destinatario", length=60, nullable=false)
	private String emailDestinatario;
	@Column(name="nome_destinatario", length=60, nullable=false)
	private String nomePessoaDestinatario;
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Email other = (Email) obj;
		if (emailDestinatario == null) {
			if (other.emailDestinatario != null)
				return false;
		} else if (!emailDestinatario.equals(other.emailDestinatario))
			return false;
		if (idEmail == null) {
			if (other.idEmail != null)
				return false;
		} else if (!idEmail.equals(other.idEmail))
			return false;
		if (nomePessoaDestinatario == null) {
			if (other.nomePessoaDestinatario != null)
				return false;
		} else if (!nomePessoaDestinatario.equals(other.nomePessoaDestinatario))
			return false;
		return true;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((emailDestinatario == null) ? 0 : emailDestinatario
						.hashCode());
		result = prime * result + ((idEmail == null) ? 0 : idEmail.hashCode());
		result = prime
				* result
				+ ((nomePessoaDestinatario == null) ? 0
						: nomePessoaDestinatario.hashCode());
		return result;
	}
	

	
}
