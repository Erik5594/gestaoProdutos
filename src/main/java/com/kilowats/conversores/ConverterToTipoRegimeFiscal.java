package com.kilowats.conversores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.kilowats.enuns.TipoRegimeFiscal;
import com.kilowats.util.Utils;

@FacesConverter(value = "converterTipoRegimeFiscal")
public class ConverterToTipoRegimeFiscal implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if(Utils.isNullOrEmpty(arg2)){
			return null;
		}
		TipoRegimeFiscal[] vetor = TipoRegimeFiscal.values();
		for(int x = 0; x < vetor.length; x++){
			if(vetor[x].getDescricaoRegime().toLowerCase().equals(arg2.toLowerCase())){
				return (TipoRegimeFiscal) vetor[x];
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return null;
	}

}
