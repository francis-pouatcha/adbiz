package org.adorsys.adcost.rest;

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
import org.adorsys.adcost.ejb.CstStmntItemLookup;
import org.adorsys.adcost.ejb.CstStmntItemPdfReportTemplate;
import org.adorsys.adcost.ejb.CstStmntItemProps;
import org.adorsys.adcost.jpa.CstStmntItem;
import org.adorsys.adcost.jpa.CstStmntItem_;
import org.adorsys.adcost.jpa.CstStmntItemSearchInput;
import org.adorsys.adcost.jpa.CstStmntItemSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/invinvtryitems")
public class CstStmntItemEndpoint extends
		CoreAbstBsnsItemEndpoint<CstStmntItem, CoreAbstBsnsItemSearchInput<CstStmntItem>, CoreAbstBsnsItemSearchResult<CstStmntItem>> {

	@Inject
	private CstStmntItemLookup lookup;
	
	@Inject
	private CstStmntItemProps entityProps;

	@Override
	protected CoreAbstBsnsItemSearchResult<CstStmntItem> newSearchResult(Long count,
			List<CstStmntItem> resultList,
			CoreAbstBsnsItemSearchInput<CstStmntItem> searchInput) {
		return new CstStmntItemSearchResult(count, resultList, searchInput);
	}

	@Override
	protected CoreAbstBsnsItemSearchInput<CstStmntItem> newSearchInput() {
		return new CstStmntItemSearchInput();
	}

	@Override
	protected CoreAbstBsnsItemLookup<CstStmntItem> getLookup() {
		return lookup;
	}

	@Override
	protected PdfReportTemplate<CstStmntItem> getReportTemplate() {
		return new CstStmntItemPdfReportTemplate();
	}

	@Override
	protected AbstEntiyProps<CstStmntItem> getEntityProps() {
		return entityProps;
	}

	@Override
	protected CoreAbstBsnsItemSearchResult<CstStmntItem> newSearchResult(
			Long count, Long total, List<CstStmntItem> resultList,
			CoreAbstBsnsItemSearchInput<CstStmntItem> searchInput) {
		return new CstStmntItemSearchResult(count, total, resultList, searchInput);
	}

	@Override
	protected Class<CstStmntItem> getEntityKlass() {
		return CstStmntItem.class;
	}
}