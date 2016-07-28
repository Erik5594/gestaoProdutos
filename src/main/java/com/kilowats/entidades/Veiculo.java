package com.kilowats.entidades;

import java.io.Serializable;

import lombok.Data;

import com.kilowats.enuns.TipoVeiculo;

public @Data class Veiculo implements Serializable{

	private static final long serialVersionUID = 1L;

	private long id;
	private String placa;
	private String chassi;
	private TipoVeiculo tipoVeiculo;
	private Pessoa cliente;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chassi == null) ? 0 : chassi.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((placa == null) ? 0 : placa.hashCode());
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
		Veiculo other = (Veiculo) obj;
		if (chassi == null) {
			if (other.chassi != null)
				return false;
		} else if (!chassi.equals(other.chassi))
			return false;
		if (id != other.id)
			return false;
		if (placa == null) {
			if (other.placa != null)
				return false;
		} else if (!placa.equals(other.placa))
			return false;
		return true;
	}
}
