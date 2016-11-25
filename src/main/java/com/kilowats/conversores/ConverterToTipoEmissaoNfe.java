package com.kilowats.conversores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.fincatto.nfe310.classes.NFTipoEmissao;
import com.kilowats.util.Utils;

@FacesConverter(value = "converterTipoEmissaoNfe")
public class ConverterToTipoEmissaoNfe implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if(Utils.isNullOrEmpty(arg2)){
			return null;
		}
		NFTipoEmissao[] vetor = NFTipoEmissao.values();
		for(int x = 0; x < vetor.length; x++){
			if(vetor[x].getDescricao().toLowerCase().equals(arg2.toLowerCase())){
				return (NFTipoEmissao) vetor[x];
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return null;
	}

}
