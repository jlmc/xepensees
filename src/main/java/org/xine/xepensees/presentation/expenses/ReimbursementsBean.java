package org.xine.xepensees.presentation.expenses;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.xine.xepensees.business.expense.entity.Currency;
import org.xine.xepensees.business.expense.entity.Expense;
import org.xine.xepensees.business.reimbursement.entity.Reimbursement;
import org.xine.xepensees.business.user.entity.User;
import org.xine.xepensees.presentation.faces.Presenter;

@Presenter
public class ReimbursementsBean implements Serializable {
	
	private static List<Reimbursement> all = new ArrayList<>();

	private Integer pageNumber;
	private Collection<Reimbursement> pageItems;
	
	@PostConstruct
	public void initialize() {
		pageNumber = 1;
		pageItems = new ArrayList<>();
		
		if (all.isEmpty()) {
			for (int i = 0; i < 76; i++) {
				final Expense expense = Expense.Builder.builder().id(1L).date(LocalDate.now()).amount(BigDecimal.TEN)
						.description("Some dummy expense").build();

				final Reimbursement reimbursement = 
						Reimbursement.builder(User.of("dummy@domain.com", "Dummy", "simple")).
							id(Long.valueOf(i)).
							version(1).
							date(LocalDate.of(2009, 3, 1)).
							currency(Currency.EURO).
								addExpense(expense).
						build();

				all.add(reimbursement);
			}
		}

	}
	
	public Collection<Reimbursement> getPageItems() {

		return pageItems;
	}
	

	public Integer getPageNumber() {
		return pageNumber;
	}
	
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

}
