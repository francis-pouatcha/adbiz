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
import org.adorsys.adcore.props.AbstEntiyProps;
import org.adorsys.adcore.rest.CoreAbstBsnsItemEndpoint;
import org.adorsys.adcore.rest.CoreAbstBsnsItemLookup;
import org.adorsys.adprocmt.jpa.PrcmtPOItem;
import org.adorsys.adprocmt.jpa.PrcmtPOItemSearchInput;
import org.adorsys.adprocmt.jpa.PrcmtPOItemSearchResult;
import org.adorsys.adprocmt.jpa.PrcmtPOItem_;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/prcmtpoitems")
public class PrcmtPOItemEndpoint extends CoreAbstBsnsItemEndpoint<PrcmtPOItem, CoreAbstBsnsItemSearchInput<PrcmtPOItem>, CoreAbstBsnsItemSearchResult<PrcmtPOItem>> {

	@Inject
	private PrcmtPOItemLookup lookup;
	
	@Inject
	private PrcmtPOItemProps props;
	
	@Override
	protected CoreAbstBsnsItemLookup<PrcmtPOItem> getLookup() {
		return lookup;
	}

	@Override
	protected Field[] getEntityFields() {
		return PrcmtPOItem_.class.getFields();
	}

	@Override
	protected CoreAbstBsnsItemSearchResult<PrcmtPOItem> newSearchResult(
			Long size, List<PrcmtPOItem> resultList,
			CoreAbstBsnsItemSearchInput<PrcmtPOItem> searchInput) {
		return new PrcmtPOItemSearchResult(size, resultList, searchInput);
	}

	@Override
	protected CoreAbstBsnsItemSearchResult<PrcmtPOItem> newSearchResult(
			Long size, Long total, List<PrcmtPOItem> resultList,
			CoreAbstBsnsItemSearchInput<PrcmtPOItem> searchInput) {
		return new PrcmtPOItemSearchResult(size, resultList, searchInput);
	}

	@Override
	protected CoreAbstBsnsItemSearchInput<PrcmtPOItem> newSearchInput() {
		return new PrcmtPOItemSearchInput();
	}

	@Override
	protected AbstEntiyProps getEntityProps() {
		return props;
	}


}