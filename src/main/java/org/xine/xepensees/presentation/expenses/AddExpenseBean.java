package org.xine.xepensees.presentation.expenses;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.xine.xepensees.business.conferences.boundary.ConferencesMng;
import org.xine.xepensees.business.conferences.entity.Conference;
import org.xine.xepensees.business.expenses.boundary.ExpensesMng;
import org.xine.xepensees.business.expenses.entity.Currency;
import org.xine.xepensees.business.expenses.entity.Expense;
import org.xine.xepensees.business.expenses.entity.ExpenseType;
import org.xine.xepensees.presentation.faces.messages.Messages;

@Named
@ViewScoped
public class AddExpenseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	ExpensesMng expensesMng;
	
	@Inject
	ConferencesMng conferencesMng;

	@Inject
	Messages messages;

	private Expense extense;

	private Conference conference;

	private final Currency[] currencies = Currency.values();

	private final ExpenseType[] expensesTypes = ExpenseType.values();
	
	private Collection<Conference> conferences = Collections.emptyList();
	

	@PostConstruct
	public void initialize() {
		this.extense = new Expense();
		this.conferences = this.conferencesMng.all();
	}

	public void save() {
		System.out.println("hello saving");
		System.out.println(this.conference);
		System.out.println(this.extense);

		try {
			this.expensesMng.create(this.extense);
			this.messages.addSucessMessageFlash("Expense created with sucess.");
			this.extense = new Expense();
		} catch (final Exception e) {
			this.messages.addErrorMessageFlash(e.getMessage());
		}
	}

	public Expense getExtense() {
		return this.extense;
	}

	public void setExtense(final Expense extense) {
		this.extense = extense;
	}

	public Conference getConference() {
		return this.conference;
	}

	public void setConference(final Conference conference) {
		this.conference = conference;
	}

	public Collection<Conference> getConferences() {
		return Collections.unmodifiableCollection(this.conferences);
	}

	public ExpenseType[] getExpensesTypes() {
		return this.expensesTypes;
	}

	public Currency[] getCurrencies() {
		return this.currencies;
	}

}
