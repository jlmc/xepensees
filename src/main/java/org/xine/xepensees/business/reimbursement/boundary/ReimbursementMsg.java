package org.xine.xepensees.business.reimbursement.boundary;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;

import org.xine.xepensees.business.expense.entity.Expense;
import org.xine.xepensees.business.params.entity.QueryParameter;
import org.xine.xepensees.business.reimbursement.entity.Reimbursement;
import org.xine.xepensees.business.user.entity.User;

@Stateless
public class ReimbursementMsg {
	
	@PersistenceContext(unitName = "x-expensees")
	EntityManager em;
	
	@Inject
	protected Validator validator;

	public Collection<Reimbursement> search (QueryParameter params) {
		final CriteriaBuilder builder = em.getCriteriaBuilder();
		final CriteriaQuery<Reimbursement> query = builder.createQuery(Reimbursement.class);
		final Root<Reimbursement> reimbursements = query.from(Reimbursement.class);
		final Join<Reimbursement, User> joinWithUser = (Join) reimbursements.fetch("user");
		final Join<Reimbursement, Expense> joinWithExpense = (Join) reimbursements.fetch("expenses");
		
		final List<Predicate> predicates = new ArrayList<>();
		
		if (params.containsNotNullValue("userId"))  {
			final Predicate usernameFilter = 
					builder.equal(
								builder.lower(builder.trim(joinWithUser.get("email"))), 
								String.valueOf(params.get("userId")).trim().toLowerCase());
			predicates.add(usernameFilter);
		}
		
		query.select(reimbursements).distinct(true).where(predicates.toArray(new Predicate[0]));
		
		final TypedQuery<Reimbursement> createQuery = em.createQuery(query);
		createQuery.setFirstResult(params.getFirtsRecord());
		createQuery.setMaxResults(params.getPageLength());
		
		return createQuery.getResultList();
	}

	public Reimbursement getReimbursement (Long id) {
		final CriteriaBuilder builder = em.getCriteriaBuilder();
		
		final CriteriaQuery<Reimbursement> query = builder.createQuery(Reimbursement.class);
		final Root<Reimbursement> reimbursements = query.from(Reimbursement.class);
		reimbursements.fetch("user");
		reimbursements.fetch("expenses");
		
		query.select(reimbursements).
			  where(builder.equal(reimbursements.get("id"), id));
		
		return em.createQuery(query).getSingleResult();
	}
	
	public Reimbursement create (@Valid Reimbursement reimbursement) {
		/*
		Set<ConstraintViolation<List<String>>> validation = validator.validate(reimbursement);
		
		if (BigDecimal.ZERO.compareTo(reimbursement.getTotal()) >= 0) {
			throw new BusinessException("The reimbursement should have a total bigger then ZERO.");
		}

		if (reimbursement.getExpenses().isEmpty()) {
			throw new BusinessException("The reimbursement should contains at least one Expense.");
		}
		*/
		final Reimbursement persistedReimbursement = em.merge(reimbursement);
		return persistedReimbursement;
	}
	
}
