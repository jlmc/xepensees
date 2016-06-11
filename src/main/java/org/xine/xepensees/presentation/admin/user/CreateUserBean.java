package org.xine.xepensees.presentation.admin.user;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.xine.xepensees.business.user.entity.User;

@Named
@RequestScoped
public class CreateUserBean {

	private User user;
	
	@PostConstruct
	public void initialize() {
		this.user = User.empty();
	}
	
	public User getUser() {
		return this.user;
	}
	
	public void create() {
		System.out.println(String.format("create user: %s ", this.user.toString()) );
	}
}
