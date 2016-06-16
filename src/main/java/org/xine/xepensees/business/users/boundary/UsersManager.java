package org.xine.xepensees.business.users.boundary;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;

import org.xine.xepensees.business.params.entity.QueryParameter;
import org.xine.xepensees.business.users.control.EncryptPassword;
import org.xine.xepensees.business.users.control.UsersRepository;
import org.xine.xepensees.business.users.entity.User;

@Stateless
public class UsersManager {
	
	@Inject
	UsersRepository usersRepository;
	@Inject
	Validator validator;
	@Inject
	Logger logger;
	@Inject
	EncryptPassword encryptPassword;
	
	public User register(@NotNull User user) {
		Set<ConstraintViolation<User>> violations = this.validator.validate(user, new Class[] {});
		
		if (! violations.isEmpty()) {
			this.logger.info("Invalid new User '"+ violations.iterator().next().getMessage() +"'.");
			
			throw new IllegalArgumentException("Invalid register User: " + violations);
		}
		
		Optional<User> checkUser = Optional.ofNullable(this.usersRepository.find(user.getEmail()));
		
		if (checkUser.isPresent()) {
			this.logger.info("The is allready a user with the email register.");
			throw new IllegalArgumentException("The Email '"+user.getEmail()+"' is Already in Use.");
		}
		
		String digestedPassword = this.encryptPassword.digest(user.getPassword());
		user.setPassword(digestedPassword);
		
		return this.usersRepository.save(user);
	}
	
	
	public Collection<User> search(@NotNull QueryParameter query) {
		return this.usersRepository.search(query);
	}

}
