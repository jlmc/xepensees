package org.xine.xepensees.business.tracing.boundary;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

@Dependent
public class LoggerExposer implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Produces
	public Logger expose(final InjectionPoint ip) {
		final String loggerName = ip.getMember().getDeclaringClass().getName();
		return Logger.getLogger(loggerName);
	}
}
