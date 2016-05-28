package org.xine.xepensees.business.expenses.boundary;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.xine.xepensees.business.expenses.IntegrationTest;
import org.xine.xepensees.business.expenses.entity.Currency;
import org.xine.xepensees.business.expenses.entity.Expense;
import org.xine.xepensees.business.expenses.entity.ExpenseType;
import org.xine.xepensees.business.tracing.boundary.LoggerExposer;

@Category(IntegrationTest.class)
@RunWith(Arquillian.class)
public class ExpensesMngIT {

	@Deployment(name = "x-expensees")
	public static Archive<?> createDeployment() {
		final WebArchive archive = ShrinkWrap.create(WebArchive.class).
				addClasses(ExpensesMng.class, LoggerExposer.class, Expense.class)
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		// System.out.println(archive.toString(true));
		return archive;
	}

	@Inject
	ExpensesMng expensesMng;

	@Test
	public void testCreate() {
		final Expense expense = new Expense();
		expense.setAmount(BigDecimal.ZERO);
		expense.setExpenseType(ExpenseType.FLIGHT);
		expense.setDate(LocalDate.now());
		expense.setCurrency(Currency.EURO);
		expense.setDescription("desc. xxxx");

		final Expense createdExpense = this.expensesMng.create(expense);

		assertNotNull(createdExpense);
	}


}
