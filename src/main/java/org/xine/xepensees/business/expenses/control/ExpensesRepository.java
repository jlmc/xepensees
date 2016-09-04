package org.xine.xepensees.business.expenses.control;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.xine.xepensees.business.conferences.entity.Conference;
import org.xine.xepensees.business.expenses.entity.Expense;
import org.xine.xepensees.business.params.entity.QueryParameter;
import org.xine.xepensees.business.persistence.control.CriteriaHelper;
import org.xine.xepensees.business.users.entity.User;

public class ExpensesRepository {
	
	@PersistenceContext(unitName = "x-expensees")
	EntityManager em;

	@Inject
	CriteriaHelper helper;

	public Expense save(Expense expense) {
		return this.em.merge(expense);
	}

	public List<Expense> search(QueryParameter parameter) {
		final CriteriaBuilder builder = this.em.getCriteriaBuilder();

		final CriteriaQuery<Expense> cQuery = builder.createQuery(Expense.class);

		final Root<Expense> expenses = cQuery.from(Expense.class);
		final Join<Expense, User> juser = (Join) expenses.fetch("user");
		final Join<Expense, Conference> jconference = (Join) expenses.fetch("conference");

		final List<Predicate> predicates = new ArrayList<>(0);

		if (parameter.containsNotNullValue("user")) {
			final Predicate userEmailLike = 
					this.helper.ilike(
								builder, 
								builder.lower(juser.get("email")),
								String.valueOf(parameter.get("user")).toLowerCase(),
								CriteriaHelper.MatchMode.EXACT);
			predicates.add(userEmailLike);
		}
		
		if (parameter.containsNotNullValue("conferenceName")) {
			final Predicate conferenceNameLike = 
					this.helper.ilike(
							builder,
							builder.lower(jconference.get("name")),
							String.valueOf(parameter.get("conferenceName")).toLowerCase(), 
							CriteriaHelper.MatchMode.ANYWHERE);
			predicates.add(conferenceNameLike);
		}

		cQuery.select(expenses).where(predicates.toArray(new Predicate[0]));

		return this.em.createQuery(cQuery).getResultList();
	}
}
