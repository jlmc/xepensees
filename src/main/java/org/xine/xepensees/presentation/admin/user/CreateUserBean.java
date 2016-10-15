package org.xine.xepensees.presentation.admin.user;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.xine.xepensees.business.user.boundary.UsersMng;
import org.xine.xepensees.business.user.entity.Permission;
import org.xine.xepensees.business.user.entity.User;
import org.xine.xepensees.presentation.faces.RedirectView;
import org.xine.xepensees.presentation.faces.messages.Messages;

@Named
@RequestScoped
public class CreateUserBean {

	private User user;
	
	private Set<Permission> permissions = new HashSet<>();

	@Inject
	UsersMng usersManager;
	
	@Inject
	Messages messages;
	
	@PostConstruct
	public void initialize() {
		this.user = User.empty();
		this.permissions.clear();
	}
	
	public User getUser() {
		return this.user;
	}
	
	public RedirectView create() {
		this.user.addPermission(this.permissions.toArray(new Permission[0]));

		this.usersManager.register(this.user);
		this.messages.addSucessMessageFlash("User created with sucess");
		initialize();

		return RedirectView.of("/admin/user/search");
	}

	public Set<Permission> getAllPermissions() {
		return EnumSet.allOf(Permission.class);
	}

	public Set<Permission> getPermissions() {
		return this.permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}
}
