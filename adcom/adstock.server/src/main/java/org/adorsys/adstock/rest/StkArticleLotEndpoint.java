package org.adorsys.adstock.rest;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchResult;
import org.adorsys.adcore.props.AbstEntiyProps;
import org.adorsys.adcore.rest.CoreAbstBsnsItemEndpoint;
import org.adorsys.adcore.rest.CoreAbstBsnsItemLookup;
import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.jpa.StkArticleLotSearchInput;
import org.adorsys.adstock.jpa.StkArticleLotSearchResult;
import org.adorsys.adstock.jpa.StkArticleLot_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/stkarticlelots")
public class StkArticleLotEndpoint extends CoreAbstBsnsItemEndpoint<StkArticleLot, CoreAbstBsnsItemSearchInput<StkArticleLot>, CoreAbstBsnsItemSearchResult<StkArticleLot>>{

	@Inject
	private StkArticleLotLookup lookup;
	@Inject
	private StkArticleLotProps entityProps;
	@Inject
	private StkArticleLotEJB ejb;
	
	@Override
	protected CoreAbstBsnsItemLookup<StkArticleLot> getLookup() {
		return lookup;
	}
	@Override
	protected Field[] getEntityFields() {
		return StkArticleLot_.class.getFields();
	}
	@Override
	protected CoreAbstBsnsItemSearchResult<StkArticleLot> newSearchResult(
			Long size, Long total, List<StkArticleLot> resultList,
			CoreAbstBsnsItemSearchInput<StkArticleLot> searchInput) {
		return new StkArticleLotSearchResult(size, total, resultList, searchInput);
	}
	@Override
	protected CoreAbstBsnsItemSearchInput<StkArticleLot> newSearchInput() {
		return new StkArticleLotSearchInput();
	}

	@Override
	protected AbstEntiyProps getEntityProps() {
		return entityProps;
	}
	@Override
	protected CoreAbstBsnsItemSearchResult<StkArticleLot> newSearchResult(
			Long size, List<StkArticleLot> resultList,
			CoreAbstBsnsItemSearchInput<StkArticleLot> searchInput) {
		return new StkArticleLotSearchResult(size, null, resultList, searchInput);
	}
	
	@GET
	@Path("/findbyartname/{artName}")
	@Produces({ "application/json", "application/xml" })
	public Response findStkArticeLotByArtName(@PathParam("artName") String artName) {
		List<StkArticleLot> list = ejb.findStkArticeLotByArtName(artName);
		return Response.ok(list).build();
	}
	
	@GET
	@Path("/findbylotpic/{lotPic}")
	@Produces({ "application/json", "application/xml" })
	public Response findStkArticeLotByLotPic(@PathParam("lotPic") String lotPic) {
		List<StkArticleLot> list = ejb.findStkArticeLotByLotPic(lotPic);
		return Response.ok(list).build();
	}
	
}