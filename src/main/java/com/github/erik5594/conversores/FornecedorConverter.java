package com.github.erik5594.conversores;

import static com.github.erik5594.util.Utils.isNullOrEmpty;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.github.erik5594.dao.FornecedorDao;
import com.github.erik5594.entidades.Fornecedor;
import com.github.erik5594.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Fornecedor.class)
public class FornecedorConverter implements Converter{
	
	private FornecedorDao fornecedorDao;
	
	public FornecedorConverter(){
		fornecedorDao = CDIServiceLocator.getBean(FornecedorDao.class);
	}

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if(isNullOrEmpty(arg2)){
			return null;
		}
		Fornecedor fornecedor = null;
		Long id = new Long(arg2);
		fornecedor = fornecedorDao.pesquisarById(id);
		return fornecedor;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
			if(arg2 != null){
				Fornecedor fornecedor = (Fornecedor) arg2;
				return fornecedor.getId() == null ? null : fornecedor.getId().toString();
		}
		return "";
	}
}
