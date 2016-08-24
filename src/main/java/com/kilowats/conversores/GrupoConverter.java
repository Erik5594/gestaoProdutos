package com.kilowats.conversores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.kilowats.dao.GrupoDao;
import com.kilowats.entidades.Grupo;
import com.kilowats.util.cdi.CDIServiceLocator;

@FacesConverter(value="grupoConverter")
public class GrupoConverter implements Converter{
	
	//@Inject
	private GrupoDao grupoDao;
	
	public GrupoConverter() {
		grupoDao = CDIServiceLocator.getBean(GrupoDao.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Grupo retorno = null;
		
		if(value != null){
			Long id = new Long(value);
			retorno = grupoDao.pesquisarById(id);
		}
		
		return retorno;
	}


	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		
		if(value != null){
			return ((Grupo) value).getId().toString();
		}
		return "";
	}

}
