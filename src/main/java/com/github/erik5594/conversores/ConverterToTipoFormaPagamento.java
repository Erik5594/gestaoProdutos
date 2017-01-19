package com.github.erik5594.conversores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.github.erik5594.enuns.FormaPagamento;
import com.github.erik5594.util.Utils;

@FacesConverter(value = "converterTipoFormaPagamento")
public class ConverterToTipoFormaPagamento implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if(Utils.isNullOrEmpty(arg2)){
			return null;
		}
		FormaPagamento[] vetor = FormaPagamento.values();
		for(int x = 0; x < vetor.length; x++){
			if(vetor[x].getDescricao().toLowerCase().equals(arg2.toLowerCase())){
				return (FormaPagamento) vetor[x];
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return null;
	}

}
