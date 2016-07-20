package com.kilowats.util.jsf;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class FacesUtils {
	
	public static void sendMensagemError(String titulo, String descricao){
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo + " " + descricao, null));
	}
	
	public static void sendMensagemAviso(String titulo, String descricao){
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, titulo + " " + descricao, null));
	}
	
	public static void sendMensagemOk(String titulo, String descricao){
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, titulo + " " + descricao, null));
	}

}
