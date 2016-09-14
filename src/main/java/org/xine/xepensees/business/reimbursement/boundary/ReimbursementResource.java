package org.xine.xepensees.business.reimbursement.boundary;

import java.util.Collection;

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
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.xine.xepensees.business.params.entity.QueryParameter;
import org.xine.xepensees.business.reimbursement.entity.Reimbursement;

@Path("reimbursements")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class ReimbursementResource {
	
	@Inject
	ReimbursementMsg reimbursementMsg;
	
	@Context
	UriInfo info;

	@GET
	@Path("{id:\\d+}")
	public Response get(@PathParam("id") Long id) {
		Reimbursement reimbursement = this.reimbursementMsg.getReimbursement(id);
		return Response.ok(reimbursement).build();
	}
	
	@POST
	public Response create(@Valid Reimbursement reimbursement) {
		Reimbursement createReimbursement = this.reimbursementMsg.create(reimbursement);
		
		UriBuilder uriBuilder = this.info.getAbsolutePathBuilder();
		uriBuilder.path(Long.toString(createReimbursement.getId()));
		
		return Response.created(uriBuilder.build()).entity(createReimbursement).build();
	}
	
	@GET
	@Path("/")
	public Response search(
			@DefaultValue("1") @QueryParam("page") Integer page,
			@DefaultValue("10") @QueryParam("size") Integer pageSize, 
			@QueryParam("user") String userId) {
		
		final QueryParameter parameters = 
				QueryParameter.with("userId", userId).page(page - 1, pageSize);
				
		Collection<Reimbursement> collection = this.reimbursementMsg.search(parameters);
		GenericEntity<Collection<Reimbursement>> reimbursements = new GenericEntity<Collection<Reimbursement>>(collection) {};
		
		return Response.ok(reimbursements).build();
	}
	
}
