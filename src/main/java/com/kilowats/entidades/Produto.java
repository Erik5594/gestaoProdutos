package com.kilowats.entidades;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

import com.kilowats.enuns.TipoProdutoUnidadeEnum;

public @Data class Produto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String codProduto;
	private String nomeProduto;
	private TipoProdutoUnidadeEnum tipoUnidade;
	private int quantidade;
	private double valor;
	private List<Ean> eans;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		Produto other = (Produto) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
