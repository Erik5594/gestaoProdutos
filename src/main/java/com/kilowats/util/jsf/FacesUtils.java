package com.kilowats.util.jsf;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

public class FacesUtils {
	@Inject
	private static FacesContext context;
	
	public static void sendMensagemError(String titulo, String descricao){
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo + " " + descricao, null));
	}
	
	public static void sendMensagemAviso(String titulo, String descricao){
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, titulo + " " + descricao, null));
	}
	
	public static void sendMensagemOk(String titulo, String descricao){
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, titulo + " " + descricao, null));
	}
}
