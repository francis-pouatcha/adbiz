package org.adorsys.adsales.api;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.adorsys.adcore.exceptions.AdException;
import org.adorsys.adsales.jpa.SlsInvoice;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/invoice")
public class SlsInvceManagerEndpoint
{

   @Inject
   private SlsInvoiceManager slsInvoiceManager; 

   
   @POST
   @Path("/saveInvoice")
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public SlsInvoiceHolder saveInvoice(SlsInvoiceHolder slsInvoiceHolder) throws AdException{
      return slsInvoiceManager.saveInvoice(slsInvoiceHolder);
   }
   
   @POST
   @Path("/clotureInvoice")
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public SlsInvoiceHolder clotureInvoice(SlsInvoiceHolder slsInvoiceHolder) throws AdException{
      return slsInvoiceManager.closeInvoice(slsInvoiceHolder);
   }
   @GET
   @Path("/cancelInvoice/{invce}")
   @Produces({ "application/json", "application/xml" })
   public SlsInvoice cancelInvoice(@PathParam("invce") String invce) throws AdException{
      return slsInvoiceManager.suspendInvoice(invce);
   }
   
   @GET
   @Path("/resumeInvoice/{invce}")
   @Produces({ "application/json", "application/xml" })
   public SlsInvoice resumeInvoice(@PathParam("invce") String invce) throws AdException{
      return slsInvoiceManager.resumeInvoice(invce);
   }
   
  
   @GET
   @Path("/deliveredInvoice/{id}")
   @Produces({ "application/json", "application/xml" })
   public SlsInvoice deliveredInvoice(@PathParam("id") String id) throws AdException{
      return slsInvoiceManager.deliveredInvoice(id);
   }
   
   
   @GET
   @Path("/{id}") //id = invceNbr
   @Produces({ "application/json", "application/xml" })
   public SlsInvoiceHolder findInvoice(@PathParam("id") String id)
   {
	   return slsInvoiceManager.findInvoice(id);
   }
   
}