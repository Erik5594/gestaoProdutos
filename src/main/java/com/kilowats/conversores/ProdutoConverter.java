package com.kilowats.conversores;

import static com.kilowats.util.Utils.isNullOrEmpty;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.kilowats.dao.ProdutoDao;
import com.kilowats.entidades.Produto;
import com.kilowats.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Produto.class)
public class ProdutoConverter implements Converter{
	
	private ProdutoDao produtoDao;
	
	public ProdutoConverter(){
		produtoDao = CDIServiceLocator.getBean(ProdutoDao.class);
	}

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if(isNullOrEmpty(arg2)){
			return null;
		}
		Produto produto = null;
		Long id = new Long(arg2);
		produto = produtoDao.pesquisarById(id);
		return produto;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
			if(arg2 != null){
				Produto produto = (Produto) arg2;
				return produto.getId() == null ? null : produto.getId().toString();
		}
		return "";
	}
}
