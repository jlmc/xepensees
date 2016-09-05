package org.xine.xepensees.business.expenses.boundary;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xine.xepensees.business.expenses.entity.Expense;
import org.xine.xepensees.business.tracing.boundary.LoggerExposer;

@RunWith(Arquillian.class)
public class ExpensesMngIT {
	// http://jsfunit.jboss.org/gettingstarted_version_2-0-0.html
	@Deployment(name = "x-expensees")
	public static Archive<?> createDeployment() {
		final WebArchive archive = ShrinkWrap.create(WebArchive.class)
				.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
				.setWebXML("web.xml")
				.addClasses(ExpensesMng.class, LoggerExposer.class, Expense.class)
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		// System.out.println(archive.toString(true));
		return archive;
	}


	@Test
	public void testCreate() {

		System.out.println("ASASSAA");

		// final Expense expense = new Expense();
		// expense.setAmount(BigDecimal.ZERO);
		// expense.setExpenseType(ExpenseType.FLIGHT);
		// expense.setDate(LocalDate.now());
		// expense.setCurrency(Currency.EURO);
		// expense.setDescription("desc. xxxx");
		//
		// final Expense createdExpense = this.expensesMng.create(expense);
		//
		// assertNotNull(createdExpense);
	}


}
