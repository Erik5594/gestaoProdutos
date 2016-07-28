package com.kilowats.conversores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.kilowats.enuns.TipoPessoa;
import com.kilowats.util.Utils;

@FacesConverter(value = "converterTipoPessoa")
public class ConverterTipoPessoa implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if(Utils.isNullOrEmpty(arg2)){
			return null;
		}
		TipoPessoa[] vetor = TipoPessoa.values();
		for(int x = 0; x < vetor.length; x++){
			if(vetor[x].descTipoPessoa.toLowerCase().equals(arg2.toLowerCase())){
				return (TipoPessoa) vetor[x];
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return null;
	}
}
