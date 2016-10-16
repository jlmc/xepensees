package org.xine.xepensees.presentation.faces.errors;

import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.validation.ConstraintViolation;

import org.xine.xepensees.business.BusinessException;

public class JsfExceptionHandler extends ExceptionHandlerWrapper {

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	private final ExceptionHandler wrapped;

	public JsfExceptionHandler(final ExceptionHandler wrapper) {
		wrapped = wrapper;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return wrapped;
	}

	@Override
	public void handle() throws FacesException {
		// all exception events on the Stack Pool
		final Iterator<ExceptionQueuedEvent> events = 
				getUnhandledExceptionQueuedEvents().
				iterator();
		
		boolean removeTheEvent = false;

		 while (events.hasNext()) {
	            final ExceptionQueuedEvent event = events.next();
	            final ExceptionQueuedEventContext context = 
	            		(ExceptionQueuedEventContext) event.getSource();

			// the throws exception
			final Throwable exceptionThrows = context.getException();
			
			final Optional<Throwable> isHandlerException = getHandlerException(exceptionThrows);

			try {

				if (exceptionThrows instanceof ViewExpiredException) {
					// the ajax request of one view Scope expired
					removeTheEvent = true;
					redirect("/");
				} else if (isHandlerException.isPresent()) {
					removeTheEvent = true;
					addFacesErrorMessage(isHandlerException.get());
				} else {
					removeTheEvent = true;
	                logger.log(Level.SEVERE, "SYSTEM ERROR: " + exceptionThrows.getMessage() + "\n"+ exceptionThrows.toString());
	                redirect("/error.xhtml");
				}

			} finally {
				// we just want to remove the handled exception
				if (removeTheEvent) {
					events.remove();
				}
			}
		 }

		// At this point, the queue will not contain any ViewExpiredEvents.
		// Therefore, let the parent handle them.
		getWrapped().handle();
	}

	private void addFacesErrorMessage(final Throwable cause) {
		final FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.getExternalContext().getFlash().setKeepMessages(true);
		final FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, cause.getMessage(), "");
		facesContext.addMessage(null, facesMessage);
	}

	private Optional<Throwable> getHandlerException(final Throwable throwable) {
		if (javax.validation.ConstraintViolationException.class.isAssignableFrom(throwable.getClass())) {
			
			final ConstraintViolation<?> violation = ((javax.validation.ConstraintViolationException) throwable)
					.getConstraintViolations().iterator().next();

			final String propertyPath = String.valueOf(violation.getPropertyPath());
			final String message = violation.getMessage();
			
			return Optional.of(new IllegalArgumentException(propertyPath + " " + message));
		}

		if (throwable instanceof BusinessException) {
			return Optional.of(throwable);
		}

		if (java.lang.IllegalArgumentException.class.isAssignableFrom(throwable.getClass())) {
			return Optional.of((Exception) throwable);
		}

		if (throwable.getCause() != null) {
			return getHandlerException(throwable.getCause());
		}

		return Optional.empty();
		
	}

	private void redirect(final String uri) {
		try {
			final FacesContext context = FacesContext.getCurrentInstance();
			final ExternalContext externalContext = context.getExternalContext();

			final String contextPath = externalContext.getRequestContextPath();

			externalContext.redirect(contextPath + uri);

			context.responseComplete();
		} catch (final IOException e) {
			throw new FacesException(e);
		}
	}
}
