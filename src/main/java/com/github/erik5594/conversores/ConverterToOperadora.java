package com.github.erik5594.conversores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.github.erik5594.enuns.Operadora;
import com.github.erik5594.util.Utils;

@FacesConverter(value = "converterOperadora")
public class ConverterToOperadora implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if(Utils.isNullOrEmpty(arg2)){
			return null;
		}
		Operadora[] vetor = Operadora.values();
		for(int x = 0; x < vetor.length; x++){
			if(vetor[x].getOperadora().toLowerCase().equals(arg2.toLowerCase())){
				return (Operadora) vetor[x];
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if(arg2 instanceof Operadora){
			if(arg2 != null){
				Operadora operadora = (Operadora) arg2;
				return operadora.getOperadora();
			}
		}
		return null;
	}
}
