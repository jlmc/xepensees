package org.xine.xepensees.business.expenses.boundary;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.xine.xepensees.business.expenses.entity.Expense;

@Stateless
public class ExpensesMng {

	@Inject
	Logger tracer;

	private static Map<Long, Expense> expenses = Collections.synchronizedMap(new HashMap<>());
	private static AtomicLong sec = new AtomicLong(0L);

	public Expense create(@NotNull final Expense expense) {
		this.tracer.info("saveing expense > " + expense);

		final long id = sec.incrementAndGet();
		expense.setId(id);
		expenses.put(id, expense);
		return expense;
	}

	public Collection<Expense> all() {
		return Collections.unmodifiableCollection(expenses.values());
	}

}
