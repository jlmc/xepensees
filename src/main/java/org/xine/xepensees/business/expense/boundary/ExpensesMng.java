package org.xine.xepensees.business.expense.boundary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;

import org.xine.xepensees.business.conference.entity.Conference;
import org.xine.xepensees.business.expense.entity.Expense;
import org.xine.xepensees.business.expense.entity.ExpenseStatus;
import org.xine.xepensees.business.params.entity.QueryParameter;
import org.xine.xepensees.business.persistence.control.CriteriaHelper;
import org.xine.xepensees.business.user.entity.User;

@Stateless
public class ExpensesMng {

	@Inject
	Logger tracer;

	@PersistenceContext(unitName = "x-expensees")
	EntityManager em;

	@Inject
	CriteriaHelper helper;

	public Expense create(@NotNull final Expense expense) {
		return em.merge(expense);
	}

	public Expense getExpense(Long id) {
		return em.find(Expense.class, id);
	}

	public Collection<Expense> search(QueryParameter parameters) {
		final CriteriaBuilder builder = em.getCriteriaBuilder();
		final CriteriaQuery<Expense> cQuery = builder.createQuery(Expense.class);

		final Root<Expense> expenses = cQuery.from(Expense.class);
		final Join<Expense, Conference> fetchWithConference = (Join) expenses.fetch("conference");
		final Join<Expense, User> fetchWithUser = (Join) expenses.fetch("user");

		final List<Predicate> predicates = new ArrayList<>();

		if (parameters.containsNotNullValue("conferenceId")) {
			predicates.add(builder.equal(fetchWithConference.get("id"), parameters.<Long>get("conferenceId")));
		}
		
		if (parameters.containsNotNullValue("userId")) {
			predicates.add(builder.equal(builder.lower(fetchWithUser.get("email")),
					parameters.<String>get("userId").toLowerCase()));
		}

		if (parameters.containsNotNullValue("status")) {
			final ExpenseStatus status = parameters.get("status");
			if (ExpenseStatus.UNREDEEMED.equals(status)) {
				predicates.add(builder.isNull(expenses.get("reimbursement")));
			} else if (ExpenseStatus.REDEEMED.equals(status)) {
				predicates.add(builder.isNotNull(expenses.get("reimbursement")));
			}
		}

		return em.createQuery(cQuery.
				select(expenses).
				where(predicates.toArray(new Predicate[0]))).
				setFirstResult(parameters.getFirtsRecord()).
				setMaxResults(parameters.getPageLength()).
				getResultList();
	}

}
