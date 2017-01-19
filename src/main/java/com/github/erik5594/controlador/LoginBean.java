package com.github.erik5594.controlador;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Named
@SessionScoped
public class LoginBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String email;
	@Inject
	private FacesContext context;
	@Inject
	private HttpServletRequest request;
	@Inject
	private HttpServletResponse response;
	
	public void preRender() {
		if("true".equals(request.getParameter("invalid"))){
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário ou Senha inválido", "Usuário ou Senha inválido"));
		}
	}
	
	public void login() throws ServletException, IOException{
		RequestDispatcher dispatcher = request.getRequestDispatcher("/j_spring_security_check");
		dispatcher.forward(request, response);
		context.responseComplete();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
