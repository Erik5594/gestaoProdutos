package com.kilowats.conversores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.kilowats.enuns.SituacaoTributariaIcms;
import com.kilowats.util.Utils;

@FacesConverter(value = "converterSituacaoTributariaIcms")
public class ConverterToSituacaoTributaria implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if(Utils.isNullOrEmpty(arg2)){
			return null;
		}
		SituacaoTributariaIcms[] vetor = SituacaoTributariaIcms.values();
		for(int x = 0; x < vetor.length; x++){
			if(vetor[x].getDescricaoIcms().toLowerCase().equals(arg2.toLowerCase())){
				return (SituacaoTributariaIcms) vetor[x];
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return null;
	}

}
