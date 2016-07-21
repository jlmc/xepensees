package org.xine.xepensees.presentation.security;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.xine.xepensees.business.security.entity.Identity;

@Named
@RequestScoped
public class IdentityBean implements Serializable {
	private static final long serialVersionUID = 1L;

	// @Inject
	private Identity identity;

	public String getUsername() {
		return String.valueOf(this.identity.getUser().getName());
	}

	public boolean isAuthenticated() {
		return this.identity.isAuthenticated();
	}

	public boolean isUser() {
		return this.identity.isUser();
	}

	public boolean isAdmin() {
		return this.identity.isAdmin();
	}

	@Inject
	public void setIdentity(Identity identity) {
		System.out.println("defining identity: " + identity);
		this.identity = identity;
	}


}
