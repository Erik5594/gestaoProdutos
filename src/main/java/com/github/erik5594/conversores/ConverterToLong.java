package com.github.erik5594.conversores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "converterLong")
public class ConverterToLong implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String valor) {
		if (valor != null && !"".equals(valor)) {
			valor = valor.replaceAll("\\D", "");
		}
		if (!valor.equals("") && valor != null) {
			try {
				return new Long(valor);
			} catch (Exception ex) {
				return 0L;
			}
		} else {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object valor) {
		if(valor != null){
			return valor.toString();
		}
		return "";
	}

}
