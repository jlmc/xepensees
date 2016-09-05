package org.xine.xepensees.business.expenses.boundary;

import java.net.URI;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.xine.xepensees.business.PaginatedListWrapper;
import org.xine.xepensees.business.expenses.entity.Expense;
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
		return this.manager.getExpense(id);
	}

	@POST
	public Response create(@Valid Expense expense) {
		final Expense created = this.manager.create(expense);

		final URI uri = this.info.getAbsolutePathBuilder().path("/" + created.getId()).build();
		return Response.created(uri).entity(created).build();
	}
	
	@GET
	public PaginatedListWrapper<Expense> search(
			@DefaultValue("1") @QueryParam("page") Integer page,
			@DefaultValue("10") @QueryParam("size") Integer pageSize, 
			@QueryParam("conferenceId") Long conferenceId,
			@QueryParam("user") String userId) {
		
		final QueryParameter parameters = 
			QueryParameter.with("conferenceId", conferenceId).
							and("userId", userId).
						page(page - 1, pageSize);
			
		return this.manager.search(parameters);
	}
	

}
