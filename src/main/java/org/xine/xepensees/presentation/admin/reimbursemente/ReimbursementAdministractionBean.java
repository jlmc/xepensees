package org.xine.xepensees.presentation.admin.reimbursemente;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.xine.xepensees.business.PaginatedListWrapper;
import org.xine.xepensees.business.conference.boundary.ConferencesMng;
import org.xine.xepensees.business.conference.entity.Conference;
import org.xine.xepensees.business.expense.boundary.ExpensesMng;
import org.xine.xepensees.business.expense.entity.Expense;
import org.xine.xepensees.business.params.entity.QueryParameter;
import org.xine.xepensees.business.user.boundary.UsersMng;
import org.xine.xepensees.business.user.entity.User;

@Named
@ViewScoped
public class ReimbursementAdministractionBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	protected ExpensesMng expensesMng;
	@Inject
	protected ConferencesMng conferenceMng;
	@Inject
	protected UsersMng usersMng;

	private User forTheUser;
	private Conference forTheConference;

	private final List<Expense> expenses = new LinkedList<>();

	private Collection<User> spekers;
	private Collection<Conference> conferences;

	private final Set<Expense> selectedExpenses = new HashSet<>();
	private final Map<Expense, Boolean> checked = new HashMap<Expense, Boolean>();
	private BigDecimal total = BigDecimal.ZERO;

	@PostConstruct
	public void searchExpenses() {
		conferences = conferenceMng.search(QueryParameter.empty());
		spekers = usersMng.search(QueryParameter.empty());

		// "userId"
		// "conferenceId"
		final QueryParameter parameter = QueryParameter.empty();
		if (forTheUser != null) {
			parameter.and("userId", forTheUser.getEmail());
		}
		if (forTheConference != null) {
			parameter.and("conferenceId", forTheConference.getId());
		}
		
		
		for (long i = 0; i < 5; i++) {
			final Expense expense = Expense.Builder.builder().amount(BigDecimal.TEN).
									description("expenses description " + i).
									date(LocalDate.now()).
									id(i).
									build();
			expenses.add(expense);
		}
	}

	public void onFiltersChange(AjaxBehaviorEvent event) {
		System.out.println("on Filters Change " + event);
		// TODO :: if the speaker is equals to previous, then do nothing
		// otherwise clean every thing else
		final QueryParameter params = QueryParameter.empty();

		if (forTheUser != null) {
			params.and("userId", forTheUser.getEmail());
		}

		if (forTheConference != null) {
			params.and("conferenceId", forTheConference.getId());
		}

		final PaginatedListWrapper<Expense> expensesWrapper = expensesMng.search(params);

		redifineExpenses(expensesWrapper.getList());
	}

	private void redifineExpenses(final List<Expense> toExpenses) {
		expenses.clear();
		expenses.addAll(toExpenses);
		total = BigDecimal.ZERO;
		checked.clear();
		selectedExpenses.clear();
	}

	public void onSelectExpense(Expense expense) {
		System.out.println("On select Expenses: " + expense);

		if (selectedExpenses.contains(expense)) {
			selectedExpenses.remove(expense);
			total = total.subtract(expense.getAmount());
		} else {
			selectedExpenses.add(expense);
			total = total.add(expense.getAmount());
		}
	}

	public List<Expense> getExpenses() {
		return Collections.unmodifiableList(expenses);
	}

	public User getForTheUser() {
		return forTheUser;
	}

	public void setForTheUser(User forTheUser) {
		this.forTheUser = forTheUser;
	}

	public void setForTheConference(Conference forTheConference) {
		this.forTheConference = forTheConference;
	}

	public Conference getForTheConference() {
		return forTheConference;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public Map<Expense, Boolean> getChecked() {
		return checked;
	}

	public Collection<User> getSpekers() {
		return spekers;
	}

	public Collection<Conference> getConferences() {
		return conferences;
	}

	

}
