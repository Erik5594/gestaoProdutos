package com.github.erik5594.conversores;

import static com.github.erik5594.util.Utils.isNullOrEmpty;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.github.erik5594.dao.VeiculoDao;
import com.github.erik5594.entidades.Veiculo;
import com.github.erik5594.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Veiculo.class)
public class VeiculoConverter implements Converter{
	
	private VeiculoDao veiculoDao;
	
	public VeiculoConverter(){
		veiculoDao = CDIServiceLocator.getBean(VeiculoDao.class);
	}

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if(isNullOrEmpty(arg2)){
			return null;
		}
		Veiculo veiculo = null;
		Long id = new Long(arg2);
		veiculo = veiculoDao.pesquisarById(id);
		return veiculo;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
			if(arg2 != null){
				Veiculo veiculo = (Veiculo) arg2;
				return veiculo.getId() == null ? null : veiculo.getId().toString();
		}
		return "";
	}
}
