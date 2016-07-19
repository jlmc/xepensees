package org.xine.xepensees.business.security.control;

import java.io.Serializable;
import java.security.Principal;
import java.util.Optional;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.xine.xepensees.business.security.entity.Anonymous;
import org.xine.xepensees.business.security.entity.Identity;
import org.xine.xepensees.business.users.control.UsersRepository;
import org.xine.xepensees.business.users.entity.User;

public class IdentityProvider implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	Principal principal;

	@Inject
	UsersRepository usersRepository;

	@Produces
	public Identity fetch() {
		final String username = this.principal.getName();
		// final User user = ("anonymous".equals(username)) ? null :
		// this.usersRepository.find(username);
		final User user = this.usersRepository.find(username);

		return Optional.ofNullable(user).
						map(u -> new Identity(u)).
						orElse(Anonymous.instance());
	}

}
