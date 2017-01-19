package com.github.erik5594.conversores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.github.erik5594.enuns.StatusOrdemServico;
import com.github.erik5594.util.Utils;

@FacesConverter(value = "converterEnumStatus")
public class ConverterToEnumStatus implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if(Utils.isNullOrEmpty(arg2)){
			return null;
		}
		StatusOrdemServico[] vetor = StatusOrdemServico.values();
		for(int x = 0; x < vetor.length; x++){
			if(vetor[x].getDescricaoStatus().toLowerCase().equals(arg2.toLowerCase())){
				return (StatusOrdemServico) vetor[x];
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return null;
	}

}
