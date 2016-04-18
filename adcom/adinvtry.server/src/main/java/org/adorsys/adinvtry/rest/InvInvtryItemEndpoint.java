package org.adorsys.adinvtry.rest;

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
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.jpa.InvInvtryItem_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/invinvtryitems")
public class InvInvtryItemEndpoint extends
		CoreAbstBsnsItemEndpoint<InvInvtryItem, CoreAbstBsnsItemSearchInput<InvInvtryItem>, CoreAbstBsnsItemSearchResult<InvInvtryItem>> {

	@Inject
	private InvInvtryItemLookup lookup;
	
	@Inject
	private InvInvtryItemProps entityProps;

	@Override
	protected Field[] getEntityFields() {
		return InvInvtryItem_.class.getFields();
	}

	@Override
	protected CoreAbstBsnsItemSearchResult<InvInvtryItem> newSearchResult(Long count,
			List<InvInvtryItem> resultList,
			CoreAbstBsnsItemSearchInput<InvInvtryItem> searchInput) {
		return new InvInvtryItemSearchResult(count, resultList, searchInput);
	}

	@Override
	protected CoreAbstBsnsItemSearchInput<InvInvtryItem> newSearchInput() {
		return new InvInvtryItemSearchInput();
	}

	@Override
	protected CoreAbstBsnsItemLookup<InvInvtryItem> getLookup() {
		return lookup;
	}

	@Override
	protected AbstEntiyProps getEntityProps() {
		return entityProps;
	}

	@Override
	protected CoreAbstBsnsItemSearchResult<InvInvtryItem> newSearchResult(
			Long count, Long total, List<InvInvtryItem> resultList,
			CoreAbstBsnsItemSearchInput<InvInvtryItem> searchInput) {
		return new InvInvtryItemSearchResult(count, total, resultList, searchInput);
	}
}