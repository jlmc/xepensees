package org.xine.xepensees.presentation.account;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.xine.xepensees.business.user.entity.User;

@Named
@RequestScoped
public class SignupBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private User user;
	
	@PostConstruct
	public void prepare() {
		user = User.empty();
	}
	
	public void createAccount() {
		System.out.println("saving");
	}
	
	
	public User getUser() {
		return user;
	}

}
