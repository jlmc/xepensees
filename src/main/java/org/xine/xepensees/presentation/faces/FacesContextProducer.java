package org.xine.xepensees.presentation.faces;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

@ApplicationScoped
public class FacesContextProducer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Produces
	@RequestScoped
	public FacesContext get() {
		return FacesContext.getCurrentInstance();
	}
	
	// @Produces
	// @SessionScoped
	// public HttpSession session() {
	// final HttpSession session = (HttpSession)
	// FacesContext.getCurrentInstance().
	// getExternalContext().getSession(true);
	// return session;
	// }

	// @Produces
	// @RequestScoped
	// public HttpServletRequest request() {
	// return (HttpServletRequest)
	// FacesContext.getCurrentInstance().getExternalContext().getRequest();
	// }

}
