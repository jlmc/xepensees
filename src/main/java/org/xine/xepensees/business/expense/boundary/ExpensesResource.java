package org.xine.xepensees.business.expense.boundary;

import java.net.URI;
import java.util.Collection;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.xine.xepensees.business.expense.entity.Expense;
import org.xine.xepensees.business.expense.entity.ExpenseStatus;
import org.xine.xepensees.business.params.entity.QueryParameter;

//@ApplicationPath("/resources")
@Path("expenses")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class ExpensesResource {

	@Inject
	ExpensesMng manager;

	@Context
	UriInfo info;

	@GET
	@Path("{id : \\d+}")
	public Expense get(@PathParam("id") final Long id) {
		return manager.getExpense(id);
	}

	@POST
	public Response create(@Valid Expense expense) {
		final Expense created = manager.create(expense);

		final URI uri = info.getAbsolutePathBuilder().path("/" + created.getId()).build();
		return Response.created(uri).entity(created).build();
	}
	
	@GET
	public Response search(
			@DefaultValue("0") @QueryParam("page") Integer page,
			@DefaultValue("10") @QueryParam("size") Integer pageSize, 
			@QueryParam("conferenceId") Long conferenceId,
			@QueryParam("userId") String userId,
			@Pattern(regexp = "(UNREDEEMED|REDEEMED)") @QueryParam("status") ExpenseStatus status) {
		
		final QueryParameter parameters = 
			QueryParameter.with("conferenceId", conferenceId).
							and("userId", userId).
							and("status", status). 
						page(page, pageSize);
			
		final Collection<Expense> expenses = manager.search(parameters);

		final GenericEntity<Collection<Expense>> genericEntity = new GenericEntity<Collection<Expense>>(expenses) {};

		return Response.ok(genericEntity).build();
	}
	

}
