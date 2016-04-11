package com.kilowats.entidades;

import java.util.List;

import com.kilowats.enuns.TipoProdutoUnidadeEnum;

public class Produto {
	private long id;
	private String codProduto;
	private String nomeProduto;
	private TipoProdutoUnidadeEnum tipoUnidade;
	private int quantidade;
	private double valor;
	private List<Ean> eans;
	
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCodProduto() {
		return codProduto;
	}
	public void setCodProduto(String codProduto) {
		this.codProduto = codProduto;
	}
	public String getNomeProduto() {
		return nomeProduto;
	}
	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}
	public TipoProdutoUnidadeEnum getTipoUnidade() {
		return tipoUnidade;
	}
	public void setTipoUnidade(TipoProdutoUnidadeEnum tipoUnidade) {
		this.tipoUnidade = tipoUnidade;
	}
	public List<Ean> getEans() {
		return eans;
	}
	public void setEans(List<Ean> eans) {
		this.eans = eans;
	}
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
