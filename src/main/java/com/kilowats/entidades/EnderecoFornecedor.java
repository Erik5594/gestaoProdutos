package com.kilowats.entidades;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
@Entity
@Table(name="endereco_fornecedor")
public @Data class EnderecoFornecedor extends Endereco implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name="id_fornecedor")
	private Fornecedor fornecedor;	
	
}
