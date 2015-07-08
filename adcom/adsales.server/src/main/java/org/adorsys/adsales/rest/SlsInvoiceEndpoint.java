package org.adorsys.adsales.rest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.adorsys.adsales.jpa.SlsInvcePymtStatus;
import org.adorsys.adsales.jpa.SlsInvoice;
import org.adorsys.adsales.jpa.SlsInvoiceSearchInput;
import org.adorsys.adsales.jpa.SlsInvoiceSearchResult;
import org.adorsys.adsales.jpa.SlsInvoice_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/slsinvoices")
public class SlsInvoiceEndpoint {

	@Inject
	private SlsInvoiceEJB ejb;

	@Inject
	private SlsInvoiceHolderEJB holderEJB;

	@POST
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public SlsInvoice create(SlsInvoice entity) {
		return detach(ejb.create(entity));
	}

	@DELETE
	@Path("/{id}")
	public Response deleteById(@PathParam("id") String id) {
		SlsInvoice deleted = ejb.deleteById(id);
		if (deleted == null)
			return Response.status(Status.NOT_FOUND).build();

		return Response.ok(detach(deleted)).build();
	}

	@PUT
	@Path("/{id}")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public SlsInvoice update(SlsInvoice entity) {
		return detach(ejb.update(entity));
	}

	@PUT
	@Path("/livre/{id}")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public SlsInvoice saleDelivery(SlsInvoice entity) {
		return ejb.saleDelivery(entity);
	}

	@GET
	@Path("/{id}")
	@Produces({ "application/json", "application/xml" })
	public Response findById(@PathParam("id") String id) {
		SlsInvoice found = ejb.findById(id);
		if (found == null)
			return Response.status(Status.NOT_FOUND).build();
		return Response.ok(detach(found)).build();
	}

	@GET
	@Produces({ "application/json", "application/xml" })
	public SlsInvoiceSearchResult listAll(@QueryParam("start") int start,
			@QueryParam("max") int max) {
		List<SlsInvoice> resultList = ejb.listAll(start, max);
		SlsInvoiceSearchInput searchInput = new SlsInvoiceSearchInput();
		searchInput.setStart(start);
		searchInput.setMax(max);
		return new SlsInvoiceSearchResult((long) resultList.size(),
				detach(resultList), detach(searchInput));
	}

	@GET
	@Path("/count")
	public Long count() {
		return ejb.count();
	}

	@POST
	@Path("/findBy")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public SlsInvoiceSearchResult findBy(SlsInvoiceSearchInput searchInput) {
		SingularAttribute<SlsInvoice, ?>[] attributes = readSeachAttributes(searchInput);
		Long count = ejb.countBy(searchInput.getEntity(), attributes);
		List<SlsInvoice> resultList = ejb.findBy(searchInput.getEntity(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		return new SlsInvoiceSearchResult(count, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countBy")
	@Consumes({ "application/json", "application/xml" })
	public Long countBy(SlsInvoiceSearchInput searchInput) {
		SingularAttribute<SlsInvoice, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countBy(searchInput.getEntity(), attributes);
	}

	@POST
	@Path("/findByLike")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public SlsInvoiceSearchResult findByLike(SlsInvoiceSearchInput searchInput) {
		SingularAttribute<SlsInvoice, ?>[] attributes = readSeachAttributes(searchInput);
		Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
		List<SlsInvoice> resultList = ejb.findByLike(searchInput.getEntity(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		List<SlsInvoice> resultList2 = holderEJB.reloadSlsInvoices(resultList);
		return new SlsInvoiceSearchResult(countLike, detach(resultList2),
				detach(searchInput));
	}

	@POST
	@Path("/findCustom")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public SlsInvoiceSearchResult findCustom(SlsInvoiceSearchInput searchInput) {
		if (searchInput.noSpecialParams()) {
			return findByLike(searchInput);
		}
		Long count = ejb.countCustom(searchInput);
		List<SlsInvoice> resultList = ejb.findCustom(searchInput);
		List<SlsInvoice> resultList2 = holderEJB.reloadSlsInvoices(resultList);
		return new SlsInvoiceSearchResult(count, detach(resultList2),
				detach(searchInput));

	}

	@POST
	@Path("/close/findByLikePay")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public SlsInvoiceSearchResult findByLikePay(SlsInvoiceSearchInput searchInput) {

		SingularAttribute<SlsInvoice, ?>[] attributes = readSeachAttributes(searchInput);
		Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
		List<SlsInvoice> resultList = ejb.findByLikePay(searchInput.getEntity(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		List<SlsInvoice> resultList2 = holderEJB.reloadSlsInvoices(resultList);
		return new SlsInvoiceSearchResult(countLike, detach(resultList2),
				detach(searchInput));
	}

	@POST
	@Path("/countByLike")
	@Consumes({ "application/json", "application/xml" })
	public Long countByLike(SlsInvoiceSearchInput searchInput) {
		SingularAttribute<SlsInvoice, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countByLike(searchInput.getEntity(), attributes);
	}

	@SuppressWarnings("unchecked")
	private SingularAttribute<SlsInvoice, ?>[] readSeachAttributes(
			SlsInvoiceSearchInput searchInput) {
		List<String> fieldNames = searchInput.getFieldNames();
		List<SingularAttribute<SlsInvoice, ?>> result = new ArrayList<SingularAttribute<SlsInvoice, ?>>();
		for (String fieldName : fieldNames) {
			Field[] fields = SlsInvoice_.class.getFields();
			for (Field field : fields) {
				if (field.getName().equals(fieldName)) {
					try {
						result.add((SingularAttribute<SlsInvoice, ?>) field
								.get(null));
					} catch (IllegalArgumentException e) {
						throw new IllegalStateException(e);
					} catch (IllegalAccessException e) {
						throw new IllegalStateException(e);
					}
				}
			}
		}
		return result.toArray(new SingularAttribute[result.size()]);
	}

	private SlsInvoice detach(SlsInvoice entity) {
		if (entity == null)
			return null;

		return entity;
	}

	private List<SlsInvoice> detach(List<SlsInvoice> list) {
		if (list == null)
			return list;
		List<SlsInvoice> result = new ArrayList<SlsInvoice>();
		for (SlsInvoice entity : list) {
			result.add(detach(entity));
		}
		return result;
	}

	private SlsInvoiceSearchInput detach(SlsInvoiceSearchInput searchInput) {
		searchInput.setEntity(detach(searchInput.getEntity()));
		return searchInput;
	}
}