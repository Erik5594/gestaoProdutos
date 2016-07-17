package com.kilowats.entidades;

import com.kilowats.annotations.ProdutoRastreador;

@ProdutoRastreador
public class Rastreador extends Produto{

	private static final long serialVersionUID = 1L;
	
	private long idRastreador;
	private Empresa fabricante;
	private boolean exigeFabricante;
	private Chip chip;
	private Veiculo veiculo;
	
	public long getIdRastreador() {
		return idRastreador;
	}
	public void setIdRastreador(long idRastreador) {
		this.idRastreador = idRastreador;
	}
	public Empresa getFabricante() {
		return fabricante;
	}
	public void setFabricante(Empresa fabricante) {
		this.fabricante = fabricante;
	}
	public boolean isExigeFabricante() {
		return exigeFabricante;
	}
	public void setExigeFabricante(boolean exigeFabricante) {
		this.exigeFabricante = exigeFabricante;
	}
	public Chip getChip() {
		return chip;
	}
	public void setChip(Chip chip) {
		this.chip = chip;
	}
	
	public Veiculo getVeiculo() {
		return veiculo;
	}
	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (int) (idRastreador ^ (idRastreador >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rastreador other = (Rastreador) obj;
		if (idRastreador != other.idRastreador)
			return false;
		return true;
	}
	
	
}
