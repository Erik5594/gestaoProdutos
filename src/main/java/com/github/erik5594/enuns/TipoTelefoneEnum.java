package com.github.erik5594.enuns;

public enum TipoTelefoneEnum {
	
	CELULAR(0, "Celular"), COMERCIAL(1, "Celular"), RESIDENCIAL(2, "Celular"), OUTRO(3, "Celular");
	
	public final int tipoTelefone;
	public final String descricaoTipoTelefone;
	
	
	TipoTelefoneEnum(int tipoTelefone, String descricaoTipoTelefone){
		this.tipoTelefone = tipoTelefone;
		this.descricaoTipoTelefone = descricaoTipoTelefone;
	}

	public String getDescricaoTipoTelefone() {
		return descricaoTipoTelefone;
	}
	
}
