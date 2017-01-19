package com.github.erik5594.conversores;

import static com.github.erik5594.util.Utils.isNullOrEmpty;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.github.erik5594.dao.ProdutoDao;
import com.github.erik5594.entidades.Produto;
import com.github.erik5594.util.cdi.CDIServiceLocator;

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
