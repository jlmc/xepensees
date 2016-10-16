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

import org.xine.xepensees.business.conference.boundary.ConferencesMng;
import org.xine.xepensees.business.conference.entity.Conference;
import org.xine.xepensees.business.expense.boundary.ExpensesMng;
import org.xine.xepensees.business.expense.entity.Expense;
import org.xine.xepensees.business.expense.entity.ExpenseStatus;
import org.xine.xepensees.business.params.entity.QueryParameter;
import org.xine.xepensees.business.reimbursement.boundary.ReimbursementMsg;
import org.xine.xepensees.business.reimbursement.entity.Reimbursement;
import org.xine.xepensees.business.user.boundary.UsersMng;
import org.xine.xepensees.business.user.entity.User;
import org.xine.xepensees.presentation.faces.RedirectView;
import org.xine.xepensees.presentation.faces.messages.Messages;

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
	@Inject
	protected ReimbursementMsg reimbursementMsg;
	@Inject
	protected Messages messages;

	private Reimbursement reimbursement;

	private User forTheUser;
	private Conference forTheConference;

	private final List<Expense> expenses = new LinkedList<>();

	private Collection<User> spekers;
	private Collection<Conference> conferences;

	private final Set<Expense> selectedExpenses = new HashSet<>();
	private final Map<Expense, Boolean> checked = new HashMap<Expense, Boolean>();
	private BigDecimal total = BigDecimal.ZERO;

	@PostConstruct
	public void initialize() {
		conferences = conferenceMng.search(QueryParameter.empty());
		spekers = usersMng.search(QueryParameter.empty());

		fetchExpenses();

		reimbursement = Reimbursement.empty();
	}

	public RedirectView create() {
		try {

			final Reimbursement reimbursement = Reimbursement.builder(forTheUser).date(LocalDate.now()).build();

			reimbursement.add(selectedExpenses.toArray(new Expense[0]));

			reimbursementMsg.create(reimbursement);
			messages.addSucessMessageFlash("Reimbursement created");
		} catch (final Exception e) {
			messages.addErrorMessageFlash(e.getMessage());
		}

		return RedirectView.of("admin/expense/reembursement");
	}

	public void onFiltersChange(AjaxBehaviorEvent event) {
		fetchExpenses();
	}

	private void fetchExpenses() {
		final QueryParameter params = QueryParameter.with("status", ExpenseStatus.UNREDEEMED);

		if (forTheUser != null) {
			params.and("userId", forTheUser.getEmail());
		}

		if (forTheConference != null) {
			params.and("conferenceId", forTheConference.getId());
		}

		redifineExpenses(expensesMng.search(params));
	}

	private void redifineExpenses(final Collection<Expense> toExpenses) {
		expenses.clear();

		// reimbursement.clear();

		expenses.addAll(toExpenses);
		total = BigDecimal.ZERO;
		checked.clear();
		selectedExpenses.clear();
	}

	public void onSelectExpense(Expense expense) {
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
