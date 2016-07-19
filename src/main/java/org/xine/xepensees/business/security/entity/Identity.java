package org.xine.xepensees.business.security.entity;

import java.io.Serializable;
import java.util.Objects;

import org.xine.xepensees.business.users.entity.User;

public class Identity implements Serializable {

	private static final long serialVersionUID = 1L;

	private final User user;

	public Identity(User user) {
		this.user = user;
	}

	public User getUser() {
		return this.user;
	}

	public boolean isUser() {
		return this.user.isUser();
	}

	public boolean isAdmin() {
		return this.user.isAdmin();
	}
	
	public boolean isAuthenticated() {
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Identity other = (Identity) obj;
		if (this.user == null) {
			if (other.user != null) {
				return false;
			}
		} else if (!this.user.equals(other.user)) {
			return false;
		}
		return true;
	}

}
