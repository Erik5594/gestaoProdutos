package com.kilowats.enuns;

public enum TipoGrupo {
	ADMINISTRADORES(0, "Administradores"), VENDEDORES(1, "Vendedores"), TECNICOS(2, "Técnico"),
	FINANCEIROS(3, "Financeiros");
	private int codGrupo;
	private String nomeGrupo;
	
	private TipoGrupo(int codGrupo, String nomeGrupo) {
		this.codGrupo = codGrupo;
		this.nomeGrupo = nomeGrupo;
	}
	
	public int getCodGrupo() {
		return codGrupo;
	}
	public void setCodGrupo(int codGrupo) {
		this.codGrupo = codGrupo;
	}
	public String getNomeGrupo() {
		return nomeGrupo;
	}
	public void setNomeGrupo(String nomeGrupo) {
		this.nomeGrupo = nomeGrupo;
	}
}
