package org.adorsys.adbase.session;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.adorsys.adbase.holder.UserWorkspaceHolder;
import org.adorsys.adbase.rest.UserWorkspaceEJB;
import org.adorsys.adcore.vo.StringHolder;
import org.apache.commons.lang3.StringUtils;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/session")
public class SessionManagerEndpoint {

	   @Inject
	   private UserWorkspaceEJB userWorkspaceEJB;
	
	   @GET
	   @Path("/login")
	   @Produces({ "application/json", "application/xml" })
	   public Response login(){
		   return Response.ok().build();
	   }

	   @GET
	   @Path("/logout")
	   @Produces({ "application/json", "application/xml" })
	   public Response logout(){
		   return Response.ok().build();
	   }

	   @GET
	   @Path("/wsin")
	   @Produces({ "application/json", "application/xml" })
	   public Response wsin(){
		   UserWorkspaceHolder holder = userWorkspaceEJB.wsin();
		   if(holder==null){
		         return Response.status(Status.NOT_FOUND).build();
		   } else {
			   return Response.ok(holder).build();
		   }
	   }


	   @POST
	   @Path("/wsout")
	   @Consumes({ "application/json", "application/xml" })
	   @Produces({ "application/json", "application/xml" })
	   public Response wsout(StringHolder identifHolder, @Context HttpServletRequest req){
		   String content = userWorkspaceEJB.wsout(identifHolder.getContent());
		   if(StringUtils.isBlank(content)){
		         return Response.status(Status.NOT_FOUND).build();
		   } else {
			   return Response.ok(new StringHolder(content)).build();
		   }
	   }
}
