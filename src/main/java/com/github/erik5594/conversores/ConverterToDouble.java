package com.github.erik5594.conversores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "converterToDouble")
public class ConverterToDouble implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String valor) {
		if (valor != null && !"".equals(valor)) {
			valor = valor.replace(".","").replace(",", ".");
		}
		if (!valor.equals("") && valor != null) {
			try {
				return Double.parseDouble(valor);
			} catch (Exception ex) {
				return 0D;
			}
		} else {
			return 0D;
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
