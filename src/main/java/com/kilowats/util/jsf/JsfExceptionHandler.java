package com.kilowats.util.jsf;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

public class JsfExceptionHandler extends ExceptionHandlerWrapper{

	private ExceptionHandler wrapped;
	
	
	public JsfExceptionHandler(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}


	@Override
	public ExceptionHandler getWrapped() {
		return wrapped;
	}
	
	@Override
	public void handle() throws FacesException {
		Iterator<ExceptionQueuedEvent> events = getUnhandledExceptionQueuedEvents().iterator();
		
		while (events.hasNext()) {
			ExceptionQueuedEvent event = events.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
			
			Throwable exception = context.getException();
			try{
			if(exception instanceof ViewExpiredException){
				redirect("/");
			}
			}finally{
				events.remove();
			}
		}
	}


	private void redirect(String page) {
		try{
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext(); 
		String contexPath = external.getRequestContextPath();
		
		external.redirect(contexPath + page);
		context.responseComplete();
		}catch(IOException ex){
			throw new FacesException(ex);
		}
	}

}
