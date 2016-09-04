package org.xine.xepensees.business.expenses.boundary;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.xine.xepensees.business.expenses.entity.Expense;
import org.xine.xepensees.business.params.entity.QueryParameter;

@Path("Expenses")
@Produces({ MediaType.APPLICATION_JSON })
public class ExpensesResource {

	@Inject
	ExpensesMng manager;

	@GET
	@Path("/")
	public Response all() {
		final QueryParameter parameters = QueryParameter.empty();
		final List<Expense> expenses = this.manager.search(parameters);

		final GenericEntity<List<Expense>> genericEntity =
				new GenericEntity<List<Expense>>(expenses) {};

		return Response.ok(genericEntity).build();

	}
	@GET
	@Path("{user}/{conference}")
	public Response conferencesOf(
			@PathParam("user") String user, 
			@PathParam("conference") String conferenceName) {
			final QueryParameter parameters = QueryParameter.
													with("user", user).
													and("conferenceName", conferenceName);
		final List<Expense> expenses = this.manager.search(parameters);
		
		final GenericEntity<List<Expense>> genericEntity =
				new GenericEntity<List<Expense>>(expenses) {};
				
		return Response.ok(genericEntity).build();
				
	}
}
