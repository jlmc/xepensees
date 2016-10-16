package org.xine.xepensees.presentation.admin.reimbursemente;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.xine.xepensees.business.expense.boundary.ExpensesMng;
import org.xine.xepensees.business.expense.entity.Currency;
import org.xine.xepensees.business.expense.entity.Expense;
import org.xine.xepensees.business.params.entity.QueryParameter;
import org.xine.xepensees.business.user.entity.User;
import org.xine.xepensees.presentation.faces.Presenter;

@Named
@Presenter
public class CreateReimbursementeBean {

	@Inject
	private ExpensesMng expensesMng;

	private Collection<Expense> expenses;

	private Currency currency;
	private User user;

	@PostConstruct
	public void initialize() {
		final Collection<Expense> result = expensesMng.search(QueryParameter.empty());
		expenses = result;
	}

	public Collection<Expense> getExpenses() {
		return expenses;
	}

	public Currency getCurrency() {
		return currency;
	}

}
