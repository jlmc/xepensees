package org.xine.xepensees.business.reimbursement.boundary;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;

import org.xine.xepensees.business.expense.entity.Expense;
import org.xine.xepensees.business.params.entity.QueryParameter;
import org.xine.xepensees.business.reimbursement.entity.Reimbursement;
import org.xine.xepensees.business.user.entity.User;

@Stateless
public class ReimbursementMsg {
	
	@PersistenceContext(unitName = "x-expensees")
	EntityManager em;
	
	public Collection<Reimbursement> search (QueryParameter params) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Reimbursement> query = builder.createQuery(Reimbursement.class);
		Root<Reimbursement> reimbursements = query.from(Reimbursement.class);
		Join<Reimbursement, User> joinWithUser = (Join) reimbursements.fetch("user");
		Join<Reimbursement, Expense> joinWithExpense = (Join) reimbursements.fetch("expenses");
		
		
		
		List<Predicate> predicates = new ArrayList<>();
		
		if (params.containsNotNullValue("userId"))  {
			Predicate usernameFilter = 
					builder.equal(builder.lower(
								  	builder.trim(
										joinWithUser.get("email"))), String.valueOf(params.get("userId")).trim().toLowerCase());
			predicates.add(usernameFilter);
		}
		
		query.select(reimbursements).distinct(true).where(predicates.toArray(new Predicate[0]));
		
		final TypedQuery<Reimbursement> createQuery = this.em.createQuery(query);
		createQuery.setFirstResult(params.getFirtsRecord());
		createQuery.setMaxResults(params.getPageLength());
		
		return createQuery.getResultList();
	}

	public Reimbursement getReimbursement (Long id) {
		CriteriaBuilder builder = this.em.getCriteriaBuilder();
		
		CriteriaQuery<Reimbursement> query = builder.createQuery(Reimbursement.class);
		Root<Reimbursement> reimbursements = query.from(Reimbursement.class);
		reimbursements.fetch("user");
		reimbursements.fetch("expenses");
		
		query.select(reimbursements).
			  where(builder.equal(reimbursements.get("id"), id));
		
		return em.createQuery(query).getSingleResult();
	}
	
	public Reimbursement create (@Valid Reimbursement reimbursement) {
		Reimbursement persistedReimbursement = this.em.merge(reimbursement);
		return persistedReimbursement;
	}
	
}
