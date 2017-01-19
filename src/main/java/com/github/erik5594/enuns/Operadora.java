package com.github.erik5594.enuns;

public enum Operadora {
CLARO(0, "Claro"),
OI(1,"Oi"),
TIM(2,"Tim"),
VIVO(3,"Vivo");

private int codOperadora;
private String operadora;

Operadora(int codOperadora, String operadora){
	this.operadora = operadora;
	this.codOperadora = codOperadora;
}

public String getOperadora() {
	return operadora;
}

public void setOperadora(String operadora) {
	this.operadora = operadora;
}

public int getCodOperadora() {
	return codOperadora;
}

public void setCodOperadora(int codOperadora) {
	this.codOperadora = codOperadora;
}
}
