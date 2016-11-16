package com.kilowats.enuns;

public enum TributacaoOrigemProduto {
	NACIONAL_EXCETO_INDICADAS_CODIGOS_3_4_5_8(0, "Nacional, exceto as indicadas nos códigos 3, 4, 5 e 8"),
	ESTRANGEIRA_IMPORTACAO_DIRETA_EXCETOINDICADA_CODIGO_6(1, "Estrangeira - Importação direta, exceto a indicada no código 6"),
	ESTRANGEIRA_ADQUIRIDA_MERCADO_INTERNO_EXCETO_INDICADA_CODIGO_7(2, "Estrangeira - Adquirida no mercado interno, exceto a indicada no código 7"),
	NACIONAL_MECADORIA_OU_BEM_COM_CONTEUDO(3, "Nacional, mercadoria ou bem com Conteúdo de Importação superior a 40% (quarenta por cento) e inferior ou igual a 70% (setenta por cento)"),
	NACIONAL_CUJO_PRODUCAO_FEITA_EM_CONFORMIDADE_PROCESSOS_PRODUTIVOS_BASICOS(4, "Nacional, cuja produção tenha sido feita em conformidade com os processos produtivos básicos de que tratam o Decreto-Lei nº 288/67, e as Leis nºs 8.248/91, 8.387/91, 10.176/01 e 11.484/07"),
	MERCADORIA_OU_BEM_CONTEUDO_IMPORTACAO(5, "Nacional, mercadoria ou bem com Conteúdo de Importação inferior ou igual a 40% (quarenta por cento)"),
	ESTRANGEIRA_IMPORTACAO_DIRETA_SEM_SIMILAR_NACIONAL_RESOLUCAO_CAMEX_E_GAS_NATURAL(6, "Estrangeira - Importação direta, sem similar nacional, constante em lista de Resolução CAMEX e gás natural"),
	ESTRANGEIRA_ADIQUIRIDA_NO_MERCADO_INTERNO_SEM_SIMILAR_NACIONAL_RESOLUCAO_CAMEX_E_GAS_NATURAL(7, "Estrangeira - Adquirida no mercado interno, sem similar nacional, constante em lista de Resolução CAMEX e gás natural"),
	NACIONA_MERCADORIA_OU_BEM_CONTEUDO_IMPORTACAO(8, "Nacional, mercadoria ou bem com Conteúdo de Importação superior a 70% (setenta por cento)");
	
	private int codOrigem;
	private String descricaoOrigem;
	
	private TributacaoOrigemProduto(int codOrigem, String descricaoOrigem) {
		this.codOrigem = codOrigem;
		this.descricaoOrigem = descricaoOrigem;
	}

	public int getCodOrigem() {
		return codOrigem;
	}

	public void setCodOrigem(int codOrigem) {
		this.codOrigem = codOrigem;
	}

	public String getDescricaoOrigem() {
		return descricaoOrigem;
	}

	public void setDescricaoOrigem(String descricaoOrigem) {
		this.descricaoOrigem = descricaoOrigem;
	}
	
}
