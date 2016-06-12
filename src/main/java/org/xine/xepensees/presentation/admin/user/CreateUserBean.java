package org.xine.xepensees.presentation.admin.user;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.xine.xepensees.business.users.boundary.UsersManager;
import org.xine.xepensees.business.users.entity.User;
import org.xine.xepensees.presentation.faces.messages.Messages;

@Named
@RequestScoped
public class CreateUserBean {

	private User user;
	
	@Inject
	UsersManager usersManager;
	
	@Inject
	Messages messages;
	
	@PostConstruct
	public void initialize() {
		this.user = User.empty();
	}
	
	public User getUser() {
		return this.user;
	}
	
	public void create() {
		this.usersManager.register(this.user);
		this.messages.addSucessMessageFlash("User created with sucess");
		this.user = User.empty();
	}
}
