package com.kilowats.enuns;

public enum TipoProdutoUnidadeEnum {
	
	UN(0, "Unidade"), KL(1, "Kilo"), MT(2, "Metro"), KIT(3, "Kit");
	
	public final int tipoUnidade;
	public final String descricaoUnidade;
	
	
	TipoProdutoUnidadeEnum(int tipoUnidade, String descricaoUnidade){
		this.tipoUnidade = tipoUnidade;
		this.descricaoUnidade = descricaoUnidade;
	}

	public String getDescricaoUnidade() {
		return descricaoUnidade;
	}
	
}
