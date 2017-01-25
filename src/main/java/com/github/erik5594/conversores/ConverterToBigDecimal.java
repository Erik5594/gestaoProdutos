package com.github.erik5594.conversores;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "converterToBigDecimal")
public class ConverterToBigDecimal implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String valor) {
		if("".equals(valor)){
			valor = "0";
		}
        BigDecimal convertedValue = new BigDecimal(valor.replace(".", "").replace(",", "."))
        	.setScale(2, RoundingMode.HALF_UP);
        return convertedValue;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object valor) {
		if(valor != null){
			return ((BigDecimal) valor).toString().replace(".", ",");
		}
		return "";
	}

}
