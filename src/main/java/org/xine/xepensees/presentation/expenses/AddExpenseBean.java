package org.xine.xepensees.presentation.expenses;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

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
	Messages messages;

	private Expense extense;

	private String conference;

	private final String[] conferences = { "devoxx United Kingdom", "devoxx Belgium", "devoxx France" };

	private final Currency[] currencies = Currency.values();

	private final ExpenseType[] expensesTypes = ExpenseType.values();

	@PostConstruct
	public void initialize() {
		this.extense = new Expense();
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
		return extense;
	}

	public void setExtense(final Expense extense) {
		this.extense = extense;
	}

	public String getConference() {
		return conference;
	}

	public void setConference(final String conference) {
		this.conference = conference;
	}

	public String[] getConferences() {
		return conferences;
	}

	public ExpenseType[] getExpensesTypes() {
		return expensesTypes;
	}

	public Currency[] getCurrencies() {
		return currencies;
	}

}
