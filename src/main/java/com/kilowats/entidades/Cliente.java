package com.kilowats.entidades;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@Entity
public @Data class Cliente extends Pessoa {

	private static final long serialVersionUID = 1L;
}
