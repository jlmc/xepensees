package org.xine.xepensees.business.expense.boundary;

import java.util.ArrayList;
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

import org.xine.xepensees.business.PaginatedListWrapper;
import org.xine.xepensees.business.conference.entity.Conference;
import org.xine.xepensees.business.expense.entity.Expense;
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
		return this.em.merge(expense);
	}

	public PaginatedListWrapper<Expense> search(QueryParameter parameter) {
		final PaginatedListWrapper<Expense> wrapper = new PaginatedListWrapper<>();
		
		wrapper.setTotalResults(count(parameter));
		wrapper.setCurrentPage(parameter.getPage() + 1);
		wrapper.setPageSize(parameter.getPageLength());

		wrapper.setList(list(parameter));
		
		
		return wrapper;
	}

	private int count(QueryParameter parameter) {
		final CriteriaBuilder builder = this.em.getCriteriaBuilder();
		final CriteriaQuery<Long> cQuery = builder.createQuery(Long.class);
		
		final Root<Expense> expenses = cQuery.from(Expense.class);
		
		final Join<Expense, User> juser = expenses.join("user");
		final Join<Expense, Conference> jconference = expenses.join("conference");

		final List<Predicate> predicates = builderFilterQuery(parameter, builder, expenses, juser, jconference);
		
        return this.em.
        			createQuery(cQuery.
        						select(builder.count(expenses)).
        						where(predicates.toArray(new Predicate[0]))).
        			getSingleResult().
        			intValue();
	}
	
	private List<Expense> list(QueryParameter parameter) {
		final CriteriaBuilder builder = this.em.getCriteriaBuilder();
		final CriteriaQuery<Expense> cQuery = builder.createQuery(Expense.class);

		final Root<Expense> expenses = cQuery.from(Expense.class);
		final Join<Expense, User> juser = (Join) expenses.fetch("user");
		final Join<Expense, Conference> jconference = (Join) expenses.fetch("conference");
		
		final List<Predicate> predicates = builderFilterQuery(parameter, builder, expenses, juser, jconference);

		return this.em.createQuery(cQuery.
									select(expenses).
									where(predicates.toArray(new Predicate[0]))).
				setFirstResult(parameter.getFirtsRecord()).
				setMaxResults(parameter.getPageLength()).
				getResultList();
	}

	private List<Predicate> builderFilterQuery(
			QueryParameter parameter,
			final CriteriaBuilder builder,
			final Root<Expense> expenses, 
			Join<Expense, User> juser, 
			Join<Expense, Conference> jconference) {
		
		final List<Predicate> predicates = new ArrayList<>(0);

		if (parameter.containsNotNullValue("userId")) {
			final Predicate userEmailLike = 
					this.helper.ilike(
								builder, 
								builder.lower(juser.get("email")),
								String.valueOf(parameter.get("userId")).toLowerCase(),
								CriteriaHelper.MatchMode.EXACT);
			predicates.add(userEmailLike);
		}
		
		if (parameter.containsNotNullValue("conferenceId")) {
			final Predicate conferenceNameLike = 
					builder.equal(jconference.get("id"), parameter.get("conferenceId"));
			predicates.add(conferenceNameLike);
		}
		return predicates;
	}

	public Expense getExpense(Long id) {
		return this.em.find(Expense.class, id);
	}

}
