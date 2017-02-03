package com.github.erik5594.conversores;

import static com.github.erik5594.util.Utils.isNullOrEmpty;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.github.erik5594.dao.DescontoDao;
import com.github.erik5594.entidades.Desconto;
import com.github.erik5594.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Desconto.class)
public class DescontoConverter implements Converter{
	
	private DescontoDao descontoDao;
	
	public DescontoConverter(){
		descontoDao = CDIServiceLocator.getBean(DescontoDao.class);
	}

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if(isNullOrEmpty(arg2)){
			return null;
		}
		Desconto desconto = null;
		Long id = new Long(arg2);
		desconto = descontoDao.pesquisarById(id);
		return desconto;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if(arg2 != null){
			Desconto desconto = (Desconto) arg2;
			return desconto.getId() == null ? null : desconto.getId().toString();
		}
		return "";
	}
}
