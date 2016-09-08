package org.xine.xepensees.business.user.control;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.xine.xepensees.business.params.entity.QueryParameter;
import org.xine.xepensees.business.persistence.control.CriteriaHelper;
import org.xine.xepensees.business.user.entity.User;

public class UsersRepository {
	
	@PersistenceContext(unitName = "x-expensees")
	EntityManager em;
	
	@Inject
	CriteriaHelper criteriaHelper;
	
	
	public User find(String email) {
		CriteriaBuilder builder = this.em.getCriteriaBuilder();
		CriteriaQuery<User> query = builder.createQuery(User.class);
		Root<User> users = query.from(User.class);
		users.fetch("permissions", JoinType.LEFT);
		
		query.select(users).
				where(
						builder.equal(
								builder.lower(builder.trim(users.get("email")))
								, String.valueOf(email).trim().toLowerCase()));
		
		TypedQuery<User> createQuery = this.em.createQuery(query);
		return this.criteriaHelper.getSingleResultUncheck(createQuery);
	}
	
	public User save (User user) {
		return this.em.merge(user);
	}

	public Collection<User> search(QueryParameter queryParameter) {
		CriteriaBuilder builder = this.em.getCriteriaBuilder();
		CriteriaQuery<User> query = builder.createQuery(User.class);
		
		Root<User> users = query.from(User.class);
		
		List<Predicate> predicates = 
				queryParameter.parameters().entrySet().stream().
				map(e -> {
					
					if ("name".equalsIgnoreCase(String.valueOf(e.getKey()))) {
						Predicate ofName = this.criteriaHelper.ilike(builder, users.get("name"), String.valueOf(e.getValue()), CriteriaHelper.MatchMode.START);
						return Optional.of(ofName);
					}
					if ("email".equalsIgnoreCase(String.valueOf(e.getKey())) || "username".equalsIgnoreCase(String.valueOf(e.getKey()))) {
						Predicate ofEmail = this.criteriaHelper.ilike(builder, users.get("email"), String.valueOf(e.getValue()), CriteriaHelper.MatchMode.EXACT);
						return Optional.of(ofEmail);
					}
					
					return Optional.<Predicate>empty();
				}).
				flatMap(o -> o.map(e -> Stream.of(e)).orElse(Stream.empty())).
				collect(Collectors.toList());
		
		query.select(users).where(predicates.toArray(new Predicate[0]));
		
		TypedQuery<User> tquery = 
					this.em.createQuery(query).
							setFirstResult(queryParameter.getFirtsRecord()).
							setMaxResults(queryParameter.getPageLength());
		
		return tquery.getResultList();
	}
	

}
