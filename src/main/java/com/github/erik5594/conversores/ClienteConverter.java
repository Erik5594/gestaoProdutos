package com.github.erik5594.conversores;

import static com.github.erik5594.util.Utils.isNullOrEmpty;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.github.erik5594.dao.ClienteDao;
import com.github.erik5594.entidades.Cliente;
import com.github.erik5594.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Cliente.class)
public class ClienteConverter implements Converter{
	
	private ClienteDao clienteDao;
	
	public ClienteConverter(){
		clienteDao = CDIServiceLocator.getBean(ClienteDao.class);
	}

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if(isNullOrEmpty(arg2)){
			return null;
		}
		Cliente cliente = null;
		Long id = new Long(arg2);
		cliente = clienteDao.pesquisarById(id);
		return cliente;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
			if(arg2 != null){
				Cliente cliente = (Cliente) arg2;
				return cliente.getId() == null ? null : cliente.getId().toString();
		}
		return "";
	}
}
