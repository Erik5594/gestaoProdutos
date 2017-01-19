package com.github.erik5594.enuns;

public enum StatusOrdemServico {
	ABERTO(0,"Aberto"),
	APROVADO(1,"Aprovado"),
	EM_ANDAMENTO(2, "Em andamento"),
	PENDENTE_CONFIRMACAO(3,"Pendente de Confirmação"),
	SERVICOS_REALIZADOS(4, "Serviços Realizados"),
	FINALIZADO(5, "Finalizado"),
	CANCELADO(6, "Cancelado");
	
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
