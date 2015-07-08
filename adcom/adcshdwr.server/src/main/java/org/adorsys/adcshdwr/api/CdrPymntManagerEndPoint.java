package org.adorsys.adcshdwr.api;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.adorsys.adcore.exceptions.AdException;

@Path("/cdrpymntmanager")
public class CdrPymntManagerEndPoint {
	@Inject
	private CdrPymntManager ejb;
	
	
	@POST
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public CdrPymntHolder save(CdrPymntHolder cdrPymntHolder) throws AdException {
		return ejb.savePymt(cdrPymntHolder);
	}
	
	   @GET
	   @Path("/{invNbr}")
	   @Produces({ "application/json", "application/xml" })
	   public CdrPymntHolder invPymt(@PathParam("invNbr") String invNbr)
	   {
	     return ejb.invPymt(invNbr);
	   }
}