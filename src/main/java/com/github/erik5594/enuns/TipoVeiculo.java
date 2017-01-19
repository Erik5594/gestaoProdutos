package com.github.erik5594.enuns;

public enum TipoVeiculo {
CARRO(0,"Carro"),
MOTO(1,"Moto"),
CAMINHAO(2,"Caminhão"),
TRATOR(3,"Trator"),
CAMINHONETE(4,"Caminhonete");

private int codTipoVeiculo;
private String tipoVeiculo;

TipoVeiculo(int codTipoVeiculo, String tipoVeiculo){
	this.codTipoVeiculo = codTipoVeiculo;
	this.tipoVeiculo = tipoVeiculo;
}

public int getCodTipoVeiculo() {
	return codTipoVeiculo;
}

public void setCodTipoVeiculo(int codTipoVeiculo) {
	this.codTipoVeiculo = codTipoVeiculo;
}

public String getTipoVeiculo() {
	return tipoVeiculo;
}

public void setTipoVeiculo(String tipoVeiculo) {
	this.tipoVeiculo = tipoVeiculo;
}


}
