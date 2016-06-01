package org.xine.xepensees.business.expenses.control;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.xine.xepensees.business.expenses.entity.Expense;

public class ExpensesRepository {
	
	@PersistenceContext(unitName = "x-expensees")
	EntityManager em;

	public Expense save(Expense expense) {
		return this.em.merge(expense);
	}
}
