package com.kilowats.enuns;

public enum StatusOrdemServico {
	ABERTO(0,"Aberto"),
	APROVADO_CLIENTE(1,"Aprovado pelo cliente"),
	RENEGOCIACAO(2,"Renegociação"),
	EM_ANDAMENTO(3, "Em andamento"),
	REPROVADO_CLIENTE(4, "Negado pelo cliente"),
	CANCELADO(5, "Cancelado"),
	FINALIZADO(6, "Finalizado");
	
	private int codStatus;
	private String descricaoStatus;
	
	private StatusOrdemServico(int codStatus, String descricaoStatus) {
		this.codStatus = codStatus;
		this.descricaoStatus = descricaoStatus;
	}

	public int getCodStatus() {
		return codStatus;
	}

	public void setCodStatus(int codStatus) {
		this.codStatus = codStatus;
	}

	public String getDescricaoStatus() {
		return descricaoStatus;
	}

	public void setDescricaoStatus(String descricaoStatus) {
		this.descricaoStatus = descricaoStatus;
	}
	
}
