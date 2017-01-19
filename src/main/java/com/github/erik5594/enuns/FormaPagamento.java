package com.github.erik5594.enuns;

public enum FormaPagamento {
	DINHEIRO(0, "Dinheiro"),
	CARTAO_DEBITO(1, "Cartão de Debito"),
	CARTAO_CREDITO(2, "Cartão de Credito"),
	CHEQUE_AVISTA(3, "Cheque à vista"),
	CHEQUE_APRAZO(4, "Cheque à prazo"),
	BOLETO(5, "Boleto"),
	OUTRO(6, "Outro");
	
	private int codPagamento;
	private String descricao;
	
	private FormaPagamento(int codPagamento, String descricao) {
		this.codPagamento = codPagamento;
		this.descricao = descricao;
	}
	
	public int getCodPagamento() {
		return codPagamento;
	}
	public void setCodPagamento(int codPagamento) {
		this.codPagamento = codPagamento;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
