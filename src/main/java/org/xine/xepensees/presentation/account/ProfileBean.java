package org.xine.xepensees.presentation.account;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.xine.xepensees.business.users.entity.User;

@Named
@ViewScoped
public class ProfileBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private User user;
	
	
	public User getUser() {
		return this.user;
	}
	
	public void update() {
		
		
	}

}
