package com.github.erik5594.util.jsf;

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.github.erik5594.exceptions.NegocioException;

public class JsfExceptionHandler extends ExceptionHandlerWrapper{

	private ExceptionHandler wrapped;
	private static Log log = LogFactory.getLog(JsfExceptionHandler.class);
	
	
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
			NegocioException negocioException = getNegocioException(exception);
			boolean handled = false;
			try{
			if(exception instanceof ViewExpiredException){
				handled = true;
				redirect("/");
			}else if(negocioException != null){
				handled = true;
				FacesUtils.sendMensagemError("Exception: ", negocioException.getMessage());
			}else{
				handled = true;
				log.error("Erro de Sistema: "+ exception.getMessage(), exception);
				redirect("/erro.xhtml");
			}
			}finally{
				if(handled){
					events.remove();
				}
			}
		}
	}


	private NegocioException getNegocioException(Throwable exception) {
		if(exception instanceof NegocioException){
			return (NegocioException) exception;
		}else if(exception.getCause() != null){
			return getNegocioException(exception.getCause());
		}
		return null;
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
