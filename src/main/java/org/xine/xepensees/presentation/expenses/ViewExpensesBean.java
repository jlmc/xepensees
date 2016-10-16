package org.xine.xepensees.presentation.expenses;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;

import org.xine.xepensees.business.expense.boundary.ExpensesMng;
import org.xine.xepensees.business.expense.entity.Expense;
import org.xine.xepensees.business.params.entity.QueryParameter;
import org.xine.xepensees.business.user.entity.User;
import org.xine.xepensees.presentation.faces.Presenter;

@Presenter
public class ViewExpensesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	protected User principalUser;

	@Inject
	protected ExpensesMng expensesMng;

	public Collection<Expense> get() {
		final QueryParameter parameters = QueryParameter.with("userId", principalUser.getEmail());
		return expensesMng.search(parameters);
	}

}
