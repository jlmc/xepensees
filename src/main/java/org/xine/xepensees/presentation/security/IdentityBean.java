package org.xine.xepensees.presentation.security;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Instance;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.xine.xepensees.business.security.entity.Identity;
import org.xine.xepensees.presentation.faces.RedirectView;
import org.xine.xepensees.presentation.faces.messages.Messages;

@Named
@SessionScoped
public class IdentityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	// @Inject
	private Identity identity;

	@Inject
	private Instance<Identity> identities;

	@Inject
	FacesContext facesContext;

	@Inject
	HttpSession session;

	@Inject
	HttpServletRequest request;

	@Inject
	Messages messages;

	public String getUsername() {
		return String.valueOf(
				getIdentity().
					getUser().
						getName());
	}

	public boolean isAuthenticated() {
		return getIdentity().isAuthenticated();
	}

	public boolean isUser() {
		return getIdentity().isUser();
	}

	public boolean isAdmin() {
		return getIdentity().isAdmin();
	}

	private Identity getIdentity() {
		if (this.identity == null) {
			this.identity = this.identities.get();
		}
		return this.identity;
	}

	/**
	 * implementation of j_security_check
	 */
	public RedirectView login() {
		try {
			final String username = this.request.getParameter("j_username");
			final String password = this.request.getParameter("j_password");
			this.request.login(username, password);
			
			cleanIdentity();
			
			return RedirectView.of("/index");
		} catch (final ServletException e) {
			this.messages.addErrorMessageFlash("invalid username or password!");
			return RedirectView.of("/login");
		}
	}

	private void cleanIdentity() {
		this.identity = null;
	}

	/**
	 * implementation of j_security_logout
	 */
	public RedirectView logout() {
		try {
			this.request.logout();

			cleanIdentity();

			//this.session.invalidate();
		} catch (final ServletException e) {
			this.messages.addSucessMessageFlash("some problem with the logout!");
		}
		return RedirectView.of("/index");
	}

}
