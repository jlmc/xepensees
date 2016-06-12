package org.xine.xepensees.business.users.control;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.xine.xepensees.business.persistence.control.CriteriaHelper;
import org.xine.xepensees.business.users.entity.User;

public class UsersRepository {
	
	@PersistenceContext(unitName = "x-expensees")
	EntityManager em;
	
	@Inject
	CriteriaHelper criteriaHelper;
	
	
	public User find(String email) {
		CriteriaBuilder builder = this.em.getCriteriaBuilder();
		CriteriaQuery<User> query = builder.createQuery(User.class);
		Root<User> users = query.from(User.class);
		query.select(users).
				where(builder.equal(
								builder.lower(builder.trim(users.get("email")))
								, String.valueOf(email).trim().toLowerCase()));
		
		TypedQuery<User> createQuery = this.em.createQuery(query);
		return this.criteriaHelper.getSingleResultUncheck(createQuery);
	}
	
	public User save (User user) {
		return this.em.merge(user);
	}
	

}
