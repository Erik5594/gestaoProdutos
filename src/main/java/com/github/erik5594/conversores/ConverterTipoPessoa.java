package com.github.erik5594.conversores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.github.erik5594.enuns.TipoPessoa;
import com.github.erik5594.util.Utils;

@FacesConverter(value = "converterTipoPessoa")
public class ConverterTipoPessoa implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if(Utils.isNullOrEmpty(arg2)){
			return null;
		}
		TipoPessoa[] vetor = TipoPessoa.values();
		for(int x = 0; x < vetor.length; x++){
			if(vetor[x].descTipoPessoa.equalsIgnoreCase(arg2)){
				return vetor[x];
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if(arg2 != null){
			if(arg2 instanceof TipoPessoa){
				return ((TipoPessoa)arg2).getDescTipoPessoa();
			}
		}
		return null;
	}
}
