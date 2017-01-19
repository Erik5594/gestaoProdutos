package com.github.erik5594.conversores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.github.erik5594.enuns.TipoProdutoUnidadeEnum;
import com.github.erik5594.enuns.TipoVeiculo;
import com.github.erik5594.util.Utils;

@FacesConverter(value = "converterTipoUnidadeProduto")
public class ConverterToTipoUnidadeProduto implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if(Utils.isNullOrEmpty(arg2)){
			return null;
		}
		TipoProdutoUnidadeEnum[] vetor = TipoProdutoUnidadeEnum.values();
		for(int x = 0; x < vetor.length; x++){
			if(vetor[x].descricaoUnidade.toLowerCase().equals(arg2.toLowerCase())){
				return (TipoProdutoUnidadeEnum) vetor[x];
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return null;
	}

}
