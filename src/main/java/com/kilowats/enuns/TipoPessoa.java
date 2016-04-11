package com.kilowats.enuns;

public enum TipoPessoa {
FISICA(0,"Fisica"), JURIDICA(1,"Juridica");

private int codPessoa;
private String descTipoPessoa;

TipoPessoa(int codPessoa, String descTipoPessoa){
	this.codPessoa = codPessoa;
	this.descTipoPessoa = descTipoPessoa;
}

public int getCodPessoa() {
	return codPessoa;
}

public void setCodPessoa(int codPessoa) {
	this.codPessoa = codPessoa;
}

public String getDescTipoPessoa() {
	return descTipoPessoa;
}

public void setDescTipoPessoa(String descTipoPessoa) {
	this.descTipoPessoa = descTipoPessoa;
}


}
