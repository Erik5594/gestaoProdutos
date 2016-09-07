package com.kilowats.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Entity
public @Data class Email implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(name="id_email")
	private Long idEmail;
	@Column(name="email_destinatario", length=60, nullable=false)
	private String emailDestinatario;
	@Column(name="nome_destinatario", length=60, nullable=false)
	private String nomePessoaDestinatario;
	@ManyToOne
	@JoinColumn(name="id_pessoa")
	private Pessoa pessoa;

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
		Email other = (Email) obj;
		if (emailDestinatario == null) {
			if (other.emailDestinatario != null)
				return false;
		} else if (!emailDestinatario.equals(other.emailDestinatario))
			return false;
		return true;
	}
	
}
