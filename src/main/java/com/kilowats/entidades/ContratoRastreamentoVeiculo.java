package com.kilowats.entidades;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(callSuper=true)
public @Data class ContratoRastreamentoVeiculo extends Contrato implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@OneToMany(mappedBy="contrado", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<Veiculo> veiculos;
}
