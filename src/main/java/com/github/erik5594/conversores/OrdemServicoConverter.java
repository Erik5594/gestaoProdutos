package com.github.erik5594.conversores;

import static com.github.erik5594.util.Utils.isNullOrEmpty;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.github.erik5594.dao.OrdemServicoDao;
import com.github.erik5594.entidades.OrdemServico;
import com.github.erik5594.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = OrdemServico.class)
public class OrdemServicoConverter implements Converter{
	
	private OrdemServicoDao ordemServicoDao;
	
	public OrdemServicoConverter(){
		ordemServicoDao = CDIServiceLocator.getBean(OrdemServicoDao.class);
	}

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if(isNullOrEmpty(arg2)){
			return null;
		}
		OrdemServico ordemServico = null;
		Long id = new Long(arg2);
		ordemServico = ordemServicoDao.pesquisarById(id);
		return ordemServico;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
			if(arg2 != null){
				OrdemServico ordemServico = (OrdemServico) arg2;
				return ordemServico.getId() == null ? null : ordemServico.getId().toString();
		}
		return "";
	}
}
