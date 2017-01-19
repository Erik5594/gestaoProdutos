package com.github.erik5594.util.jsf;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class FacesUtils {
	
	public static boolean isPostback() {
		return FacesContext.getCurrentInstance().isPostback();
	}
	
	public static boolean isNotPostback() {
		return !isPostback();
	}
	public static void sendMensagemError(String titulo, String descricao){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, titulo + " " + descricao, null));
	}
	
	public static void sendMensagemAviso(String titulo, String descricao){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, titulo + " " + descricao, null));
	}
	
	public static void sendMensagemOk(String titulo, String descricao){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, titulo + " " + descricao, null));
	}
}
