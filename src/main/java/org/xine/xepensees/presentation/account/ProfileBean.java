package org.xine.xepensees.presentation.account;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.xine.xepensees.business.security.entity.Identity;
import org.xine.xepensees.business.users.entity.User;

@Named
@ViewScoped
public class ProfileBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private User user;
	
	@Inject
	private Identity identity;

	@PostConstruct
	private void initialize() {
		this.user = this.identity.getUser();
	}
	
	public User getUser() {
		return this.user;
	}
	


}
