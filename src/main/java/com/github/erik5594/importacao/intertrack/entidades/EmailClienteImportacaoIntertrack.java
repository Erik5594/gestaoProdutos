package com.github.erik5594.importacao.intertrack.entidades;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.github.erik5594.entidades.Email;

@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name="email_cliente_importacao_intertrack")
public @Data class EmailClienteImportacaoIntertrack extends Email implements Serializable{

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_cliente_importacao_intertrack", nullable=false)
	private ClienteImportacaoIntertrack clienteImportacaoIntertrack;
	
}
