package org.xine.xepensees.presentation.admin.user;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.xine.xepensees.business.user.entity.User;

@Named
@ViewScoped
public class SearchUserBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Collection<User> users;
	
	public void initialize() {
		this.users = Collections.emptyList();
	}
	
	public Collection<User> getUsers() {
		return this.users;
	}
	
	public void search() {
		//TODO:::
	}

}
