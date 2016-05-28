package org.xine.xepensees.presentation.faces.errors;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class JsfExceptionHandlerFactory extends ExceptionHandlerFactory {

	private final ExceptionHandlerFactory parent;

	public JsfExceptionHandlerFactory(final ExceptionHandlerFactory parent) {
		this.parent = parent;
	}

	@Override
	public ExceptionHandler getExceptionHandler() {
		return new JsfExceptionHandler(this.parent.getExceptionHandler());
	}

}
