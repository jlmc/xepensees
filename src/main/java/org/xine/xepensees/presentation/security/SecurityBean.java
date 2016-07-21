package org.xine.xepensees.presentation.security;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.xine.xepensees.presentation.faces.RedirectView;
import org.xine.xepensees.presentation.faces.messages.Messages;

@Named
@RequestScoped
public class SecurityBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	FacesContext facesContext;
	@Inject
	Messages messages;

	/**
	 * implementation of j_security_check
	 */
	public RedirectView login() {
		try {
			final HttpServletRequest request = (HttpServletRequest) this.facesContext.getExternalContext().getRequest();
			final String username = request.getParameter("j_username");
			final String password = request.getParameter("j_password");
			request.login(username, password);
			return RedirectView.of("index");
		} catch (final ServletException e) {
			this.messages.addErrorMessageFlash("Incorrect Username or Password!");
			return RedirectView.of("login");
		}
	}

	/**
	 * implementation of j_security_logout
	 * 
	 */
	public RedirectView logout() {
		final HttpServletRequest request = (HttpServletRequest) this.facesContext.getExternalContext().getRequest();
		try {
			request.logout();
		} catch (final ServletException e) {
			this.messages.addSucessMessageFlash("some problem with the logout!");
		}
		return RedirectView.of("index");
	}

}
