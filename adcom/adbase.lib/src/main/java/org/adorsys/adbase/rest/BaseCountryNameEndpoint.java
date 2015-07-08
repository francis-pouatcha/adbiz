package org.adorsys.adbase.rest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.adorsys.adbase.jpa.BaseCountryName;
import org.adorsys.adbase.jpa.BaseCountryName_;
import org.adorsys.adbase.jpa.BaseCountryNameSearchInput;
import org.adorsys.adbase.jpa.BaseCountryNameSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/basecountrynames")
public class BaseCountryNameEndpoint {

	@Inject
	private BaseCountryNameEJB ejb;

	@GET
	@Path("/{identif}")
	@Produces({ "application/json", "application/xml" })
	public Response findByIdentif(@PathParam("identif") String identif) {
		BaseCountryName found = ejb.findById(identif);
		if (found == null)
			return Response.status(Status.NOT_FOUND).build();
		return Response.ok(detach(found)).build();
	}

	@GET
	@Produces({ "application/json", "application/xml" })
	public BaseCountryNameSearchResult listAll(@QueryParam("start") int start,
			@QueryParam("max") int max) {
		List<BaseCountryName> resultList = ejb.listAll(start, max);
		BaseCountryNameSearchInput searchInput = new BaseCountryNameSearchInput();
		searchInput.setStart(start);
		searchInput.setMax(max);
		return new BaseCountryNameSearchResult((long) resultList.size(),
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
	public BaseCountryNameSearchResult findBy(
			BaseCountryNameSearchInput searchInput) {
		SingularAttribute<BaseCountryName, ?>[] attributes = readSeachAttributes(searchInput);
		Long count = ejb.countBy(searchInput.getEntity(), attributes);
		List<BaseCountryName> resultList = ejb.findBy(searchInput.getEntity(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		return new BaseCountryNameSearchResult(count, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countBy")
	@Consumes({ "application/json", "application/xml" })
	public Long countBy(BaseCountryNameSearchInput searchInput) {
		SingularAttribute<BaseCountryName, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countBy(searchInput.getEntity(), attributes);
	}

	@POST
	@Path("/findByLike")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public BaseCountryNameSearchResult findByLike(
			BaseCountryNameSearchInput searchInput) {
		SingularAttribute<BaseCountryName, ?>[] attributes = readSeachAttributes(searchInput);
		Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
		List<BaseCountryName> resultList = ejb.findByLike(
				searchInput.getEntity(), searchInput.getStart(),
				searchInput.getMax(), attributes);
		return new BaseCountryNameSearchResult(countLike, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countByLike")
	@Consumes({ "application/json", "application/xml" })
	public Long countByLike(BaseCountryNameSearchInput searchInput) {
		SingularAttribute<BaseCountryName, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countByLike(searchInput.getEntity(), attributes);
	}

	@SuppressWarnings("unchecked")
	private SingularAttribute<BaseCountryName, ?>[] readSeachAttributes(
			BaseCountryNameSearchInput searchInput) {
		List<String> fieldNames = searchInput.getFieldNames();
		List<SingularAttribute<BaseCountryName, ?>> result = new ArrayList<SingularAttribute<BaseCountryName, ?>>();
		for (String fieldName : fieldNames) {
			Field[] fields = BaseCountryName_.class.getFields();
			for (Field field : fields) {
				if (field.getName().equals(fieldName)) {
					try {
						result.add((SingularAttribute<BaseCountryName, ?>) field
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

	private BaseCountryName detach(BaseCountryName entity) {
		if (entity == null)
			return null;

		return entity;
	}

	private List<BaseCountryName> detach(List<BaseCountryName> list) {
		if (list == null)
			return list;
		List<BaseCountryName> result = new ArrayList<BaseCountryName>();
		for (BaseCountryName entity : list) {
			result.add(detach(entity));
		}
		return result;
	}

	private BaseCountryNameSearchInput detach(
			BaseCountryNameSearchInput searchInput) {
		searchInput.setEntity(detach(searchInput.getEntity()));
		return searchInput;
	}
}