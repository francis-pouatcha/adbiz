package org.adorsys.adstock.rest;

import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchResult;
import org.adorsys.adcore.pdfreport.PdfReportTemplate;
import org.adorsys.adcore.props.AbstEntiyProps;
import org.adorsys.adcore.rest.CoreAbstBsnsItemEndpoint;
import org.adorsys.adcore.rest.CoreAbstBsnsItemLookup;
import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.jpa.StkArticleLot_;
import org.adorsys.adstock.jpa.StkArticleLotSearchInput;
import org.adorsys.adstock.jpa.StkArticleLotSearchResult;

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
			Long size, List<StkArticleLot> resultList,
			CoreAbstBsnsItemSearchInput<StkArticleLot> searchInput) {
		return new StkArticleLotSearchResult(size, resultList, searchInput);
	}
	@Override
	protected CoreAbstBsnsItemSearchInput<StkArticleLot> newSearchInput() {
		return new StkArticleLotSearchInput();
	}
	@Override
	protected PdfReportTemplate<StkArticleLot> getReportTemplate() {
		return new StkArticleLotPdfReportTemplate();
	}
	@Override
	protected AbstEntiyProps<StkArticleLot> getEntityProps() {
		return entityProps;
	}

}