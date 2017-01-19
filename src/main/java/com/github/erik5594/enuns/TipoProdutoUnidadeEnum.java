package com.github.erik5594.enuns;

public enum TipoProdutoUnidadeEnum {
	
	UN(0, "Unidade"), KG(1, "Kilo"), MT(2, "Metro"), KIT(3, "Kit"), PC(4, "Peça"), SV(5, "Serviço");
	
	public final int tipoUnidade;
	public final String descricaoUnidade;
	
	
	TipoProdutoUnidadeEnum(int tipoUnidade, String descricaoUnidade){
		this.tipoUnidade = tipoUnidade;
		this.descricaoUnidade = descricaoUnidade;
	}

	public String getDescricaoUnidade() {
		return descricaoUnidade;
	}
	
	public int getTipoUnidade(){
		return tipoUnidade;
	}
	
}
