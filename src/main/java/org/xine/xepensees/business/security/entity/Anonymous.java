package org.xine.xepensees.business.security.entity;

import org.xine.xepensees.business.user.entity.User;

public class Anonymous extends Identity {

	private static final long serialVersionUID = 1L;

	private Anonymous(User user) {
		super(user);
	}

	public static Identity instance() {
		return new Anonymous(User.of("Anonymous", "Anonymous", "Anonymous"));
	}

	@Override
	public boolean isAuthenticated() {
		return false;
	}


}
