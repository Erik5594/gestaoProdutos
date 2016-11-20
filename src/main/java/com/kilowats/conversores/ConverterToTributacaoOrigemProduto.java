package com.kilowats.conversores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.kilowats.enuns.TributacaoOrigemProduto;
import com.kilowats.util.Utils;

@FacesConverter(value = "converterTributacaoOrigemProduto")
public class ConverterToTributacaoOrigemProduto implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if(Utils.isNullOrEmpty(arg2)){
			return null;
		}
		TributacaoOrigemProduto[] vetor = TributacaoOrigemProduto.values();
		for(int x = 0; x < vetor.length; x++){
			if(vetor[x].getDescricaoOrigem().toLowerCase().equals(arg2.toLowerCase())){
				return (TributacaoOrigemProduto) vetor[x];
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return null;
	}

}
