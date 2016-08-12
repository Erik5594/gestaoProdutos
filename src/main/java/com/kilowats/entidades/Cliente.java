package com.kilowats.entidades;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
public @Data class Cliente extends Pessoa {

	private static final long serialVersionUID = 1L;
}
