package com.kilowats.conversores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.kilowats.enuns.TipoGrupo;
import com.kilowats.util.Utils;

@FacesConverter(value = "converterNomeGrupo")
public class ConverterToNomeGrupo implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String nomeGrupo) {
		if(Utils.isNullOrEmpty(nomeGrupo)){
			return null;
		}
		TipoGrupo[] vetorGrupo = TipoGrupo.values();
		for(int x = 0; x < vetorGrupo.length; x++){
			if(vetorGrupo[x].name().toUpperCase().equals(nomeGrupo.toUpperCase())){
				return (TipoGrupo) vetorGrupo[x];
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if(arg2 instanceof TipoGrupo){
			if(arg2 != null){
				TipoGrupo tipoGrupo = (TipoGrupo) arg2;
				return tipoGrupo.getNomeGrupo();
			}
		}
		return null;
	}
}
