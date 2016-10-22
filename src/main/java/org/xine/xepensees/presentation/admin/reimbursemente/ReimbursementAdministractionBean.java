package org.xine.xepensees.presentation.admin.reimbursemente;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
	private Conference forTheConference;

	private final List<Expense> availableExpenses = new LinkedList<>();

	private Collection<User> spekers;
	private Collection<Conference> availableConferences;

	private final Map<Expense, Boolean> checked = new HashMap<Expense, Boolean>();


	@PostConstruct
	public void initialize() {
		reimbursement = Reimbursement.empty();

		availableConferences = conferenceMng.search(QueryParameter.empty());
		spekers = usersMng.search(QueryParameter.empty());
		// fetchExpenses();
	}

	public RedirectView create() {
		reimbursement.setDate(LocalDate.now());

		reimbursementMsg.create(reimbursement);
		messages.addSucessMessageFlash("Reimbursement created");

		return RedirectView.of("/admin/expense/reembursement");
	}

	public void onFiltersChange(AjaxBehaviorEvent event) {
		fetchExpenses();
	}

	private void fetchExpenses() {
		final QueryParameter params = QueryParameter.with("status", ExpenseStatus.UNREDEEMED);

		if (reimbursement.getUser() != null) {
			params.and("userId", reimbursement.getUser().getEmail());
		}

		if (forTheConference != null) {
			params.and("conferenceId", forTheConference.getId());
		}

		redifineExpenses(expensesMng.search(params));
	}

	private void redifineExpenses(final Collection<Expense> toExpenses) {
		availableExpenses.clear();
		availableExpenses.addAll(toExpenses);

		reimbursement.clear();
		checked.clear();
	}

	public void onSelectExpense(Expense expense) {
		if (reimbursement.contains(expense)) {
			reimbursement.remove(expense);
		} else {
			reimbursement.add(expense);
		}
	}

	public List<Expense> getAvailableExpenses() {
		return Collections.unmodifiableList(availableExpenses);
	}

	public void setForTheConference(Conference forTheConference) {
		this.forTheConference = forTheConference;
	}

	public Conference getForTheConference() {
		return forTheConference;
	}

	public Map<Expense, Boolean> getChecked() {
		return checked;
	}

	public Collection<User> getSpekers() {
		return spekers;
	}

	public Collection<Conference> getAvailableConferences() {
		return Collections.unmodifiableCollection(availableConferences);
	}

	public Reimbursement getReimbursement() {
		return reimbursement;
	}

	public void setReimbursement(Reimbursement reimbursement) {
		this.reimbursement = reimbursement;
	}

}
