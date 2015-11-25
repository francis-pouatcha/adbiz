package org.adorsys.adprocmt.api;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchResult;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchInput;
import org.adorsys.adcore.rest.CoreAbstBsnsManagerEndpoint;
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectManager;
import org.adorsys.adprocmt.jpa.PrcmtDelivery;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryCstr;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryHstry;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItemSearchInput;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItemSearchResult;
import org.adorsys.adprocmt.jpa.PrcmtJob;
import org.adorsys.adprocmt.jpa.PrcmtStep;
import org.adorsys.adprocmt.rest.PrcmtDeliveryInjector;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/delivery")
public class PrcmtDeliveryManagerEndpoint extends CoreAbstBsnsManagerEndpoint<PrcmtDelivery, PrcmtDlvryItem, 
	PrcmtDeliveryHstry, PrcmtJob, PrcmtStep, PrcmtDeliveryCstr> {

	@Inject
	private PrcmtDeliveryManager manager;
	@Inject
	private PrcmtDeliveryInjector injector;
	
	@Override
	protected CoreAbstBsnsObjectManager<PrcmtDelivery, PrcmtDlvryItem, PrcmtDeliveryHstry, PrcmtJob, PrcmtStep, PrcmtDeliveryCstr, CoreAbstBsnsObjectSearchInput<PrcmtDelivery>> getBsnsObjManager() {
		return manager;
	}

	@Override
	protected CoreAbstBsnsObjInjector<PrcmtDelivery, PrcmtDlvryItem, PrcmtDeliveryHstry, PrcmtJob, PrcmtStep, PrcmtDeliveryCstr> getInjector() {
		return injector;
	}

	@Override
	protected CoreAbstBsnsItemSearchInput<PrcmtDlvryItem> newItemSearchInput() {
		return new PrcmtDlvryItemSearchInput();
	}

	@Override
	protected CoreAbstBsnsItemSearchResult<PrcmtDlvryItem> newItemSearchResult(
			long count, List<PrcmtDlvryItem> resultList,
			CoreAbstBsnsItemSearchInput<PrcmtDlvryItem> itemSearchInput) {
		return new PrcmtDlvryItemSearchResult(count, resultList, itemSearchInput);
	}
}