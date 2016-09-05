package com.kilowats.entidades;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@Entity
public @Data class Cliente extends Pessoa {

	private static final long serialVersionUID = 1L;
	@OneToMany(mappedBy="cliente")
	private List<Veiculo> veiculos;
}
