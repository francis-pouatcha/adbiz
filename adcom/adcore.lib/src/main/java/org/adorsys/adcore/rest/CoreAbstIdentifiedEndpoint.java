package org.adorsys.adcore.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.adorsys.adcore.jpa.CoreAbstIdentifObject;

public abstract class CoreAbstIdentifiedEndpoint<E extends CoreAbstIdentifObject> extends CoreAbstIdentifReadEndpoint<E>{

	protected abstract CoreAbstIdentifiedEJB<E> getEjb();
	
	@POST
	@Consumes({ "application/json"})
	@Produces({ "application/json"})
	public E create(E entity) {
		return detach(getEjb().create(entity));
	}

	@DELETE
	@Path("/{id}")
	public Response deleteById(@PathParam("id") String id) {
		E deleted = getEjb().deleteById(id);
		if (deleted == null)
			return Response.status(Status.NOT_FOUND).build();

		return Response.ok(detach(deleted)).build();
	}

	@PUT
	@Path("/{id}")
	@Produces({ "application/json"})
	@Consumes({ "application/json"})
	public E update(E entity, @PathParam("id") String id) {
		// TODO: verify id integrity.
		return detach(getEjb().update(entity));
	}


}