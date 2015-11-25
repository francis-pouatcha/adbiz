package org.adorsys.adprocmt.rest;

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
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItemSearchInput;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItemSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/prcmtdlvryitems")
public class PrcmtDlvryItemEndpoint extends
	CoreAbstBsnsItemEndpoint<PrcmtDlvryItem, CoreAbstBsnsItemSearchInput<PrcmtDlvryItem>, CoreAbstBsnsItemSearchResult<PrcmtDlvryItem>> {

	@Inject
	private PrcmtDlvryItemLookup lookup;
	
	@Inject
	private PrcmtDlvryItemProps props;
	
	@Override
	protected CoreAbstBsnsItemLookup<PrcmtDlvryItem> getLookup() {
		return lookup;
	}

	@Override
	protected Field[] getEntityFields() {
		return PrcmtDlvryItem_.class.getFields();
	}

	@Override
	protected CoreAbstBsnsItemSearchResult<PrcmtDlvryItem> newSearchResult(
			Long size, List<PrcmtDlvryItem> resultList,
			CoreAbstBsnsItemSearchInput<PrcmtDlvryItem> searchInput) {
		return new PrcmtDlvryItemSearchResult(size, resultList, searchInput);
	}

	@Override
	protected CoreAbstBsnsItemSearchResult<PrcmtDlvryItem> newSearchResult(
			Long size, Long total, List<PrcmtDlvryItem> resultList,
			CoreAbstBsnsItemSearchInput<PrcmtDlvryItem> searchInput) {
		return new PrcmtDlvryItemSearchResult(size, resultList, searchInput);
	}

	@Override
	protected CoreAbstBsnsItemSearchInput<PrcmtDlvryItem> newSearchInput() {
		return new PrcmtDlvryItemSearchInput();
	}

	@Override
	protected PdfReportTemplate<PrcmtDlvryItem> getReportTemplate() {
		return new PrcmtDlvryItemPdfReportTemplate() ;
	}

	@Override
	protected AbstEntiyProps<PrcmtDlvryItem> getEntityProps() {
		return props;
	}


}