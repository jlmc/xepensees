package org.xine.xepensees.presentation.admin.expenses;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.xine.xepensees.business.PaginatedListWrapper;
import org.xine.xepensees.business.expenses.boundary.ExpensesMng;
import org.xine.xepensees.business.expenses.entity.Expense;
import org.xine.xepensees.business.params.entity.QueryParameter;
import org.xine.xepensees.presentation.faces.Presenter;

@Presenter
public class ReembursementBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private PaginatedListWrapper<Expense> paginatedListWrapper;

	@Inject
	ExpensesMng manager;

	@PostConstruct
	public void initiaize() {
		final QueryParameter parameter = QueryParameter.empty().page(0, 10);

		this.paginatedListWrapper = this.manager.search(parameter);
	}

	public PaginatedListWrapper<Expense> getPaginatedListWrapper() {
		return this.paginatedListWrapper;
	}


}
