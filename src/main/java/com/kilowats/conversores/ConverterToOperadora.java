package com.kilowats.conversores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.kilowats.enuns.Operadora;
import com.kilowats.util.Utils;

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
		return null;
	}
}
