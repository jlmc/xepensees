package org.xine.xepensees.presentation.admin.expenses;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.PostConstruct;

import org.xine.xepensees.business.expenses.entity.Expense;
import org.xine.xepensees.presentation.faces.Presenter;

@Presenter
public class ReembursementBean implements Serializable {
	
	private String conferenceNama;
	private String userName;

	private Collection<Expense> expenses;

	@PostConstruct
	public void initiaize() {

	}


}
