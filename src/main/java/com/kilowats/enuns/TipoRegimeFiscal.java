package com.kilowats.enuns;

public enum TipoRegimeFiscal {
	TRIBUTACAO_NORMAL(0, "Tributacao Normal"),
	SIMPLES_NACIONAL(1, "Simples Nacional");
	
	private int codRegime;
	private String descricaoRegime;
	
	private TipoRegimeFiscal(int codRegime, String descricaoRegime) {
		this.codRegime = codRegime;
		this.descricaoRegime = descricaoRegime;
	}

	public int getCodRegime() {
		return codRegime;
	}

	public void setCodRegime(int codRegime) {
		this.codRegime = codRegime;
	}

	public String getDescricaoRegime() {
		return descricaoRegime;
	}

	public void setDescricaoRegime(String descricaoRegime) {
		this.descricaoRegime = descricaoRegime;
	}
	
}
