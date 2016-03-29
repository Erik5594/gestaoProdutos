package com.kilowats.entidades;

import java.util.List;

import com.kilowats.enuns.TipoProdutoUnidadeEnum;

public class Produto {
	private long id;
	private int codProduto;
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
	public int getCodProduto() {
		return codProduto;
	}
	public void setCodProduto(int codProduto) {
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
}
