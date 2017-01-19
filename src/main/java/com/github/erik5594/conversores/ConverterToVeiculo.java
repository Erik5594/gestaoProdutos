package com.github.erik5594.conversores;

import static com.github.erik5594.util.Utils.isNullOrEmpty;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.github.erik5594.enuns.Operadora;
import com.github.erik5594.enuns.TipoVeiculo;

@FacesConverter(value = "converterTipoVeiculo")
public class ConverterToVeiculo implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if(isNullOrEmpty(arg2)){
			return null;
		}
		TipoVeiculo[] vetor = TipoVeiculo.values();
		for(int x = 0; x < vetor.length; x++){
			if(vetor[x].getTipoVeiculo().toLowerCase().equals(arg2.toLowerCase())){
				return (TipoVeiculo) vetor[x];
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if(arg2 instanceof TipoVeiculo){
			if(arg2 != null){
				TipoVeiculo tipoVeiculo = (TipoVeiculo) arg2;
				return tipoVeiculo.getTipoVeiculo();
			}
		}
		return null;
	}
}
