package org.xine.xepensees.presentation.admin.user;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.xine.xepensees.business.params.entity.QueryParameter;
import org.xine.xepensees.business.users.boundary.UsersManager;
import org.xine.xepensees.business.users.entity.User;

@Named
@ViewScoped
public class SearchUserBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsersManager usersManager;
	
	private String searchFor;
	private String searchValue;
	private int page;
	private Collection<User> users;
	
	public void initialize() {
		this.users = Collections.emptyList();
	}
	
	public Collection<User> getUsers() {
		return this.users;
	}
	
	public void search() {
		this.page = 0;
		QueryParameter query = createQueryParameter();
		this.search(query);
	}

	private QueryParameter createQueryParameter() {
		QueryParameter query = QueryParameter.empty().page(this.page, 10);
		
		if (this.searchValue != null && !this.searchValue.trim().isEmpty()) {
			if ("email".equals(this.searchFor)) {
				query.and("email", this.searchValue.trim());
			}
			if ("name".equals(this.searchFor)) {
				query.and("name", this.searchValue.trim());
			}
		}
		return query;
	}
	
	public void previous() {
		this.page--;
		QueryParameter query = createQueryParameter();
		this.search(query);
	}
	
	public void forward() {
		this.page++;
		QueryParameter query = QueryParameter.empty().page(this.page, 10);
		this.search(query);
	}
	
	private void search(QueryParameter query) {
		this.users = this.usersManager.search(query);
	}
	
	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}
	
	public String getSearchFor() {
		return this.searchFor;
	}
	
	public String getSearchValue() {
		return this.searchValue;
	}
	
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	
	public int getPageNum() {
		return this.page;
	}
	
}
