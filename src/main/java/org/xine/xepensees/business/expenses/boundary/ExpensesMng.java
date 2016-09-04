package org.xine.xepensees.business.expenses.boundary;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.xine.xepensees.business.expenses.control.ExpensesRepository;
import org.xine.xepensees.business.expenses.entity.Expense;
import org.xine.xepensees.business.params.entity.QueryParameter;

@Stateless
public class ExpensesMng {

	@Inject
	Logger tracer;
	
	@Inject
	ExpensesRepository expensesRepository;

	public Expense create(@NotNull final Expense expense) {
		return this.expensesRepository.save(expense);
	}

	public List<Expense> search(QueryParameter parameter) {
		return this.expensesRepository.search(parameter);
	}

}
