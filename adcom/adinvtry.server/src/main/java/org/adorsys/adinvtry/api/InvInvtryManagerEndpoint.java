package org.adorsys.adinvtry.api;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Path;

import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchResult;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchInput;
import org.adorsys.adcore.rest.CoreAbstArchiveManager;
import org.adorsys.adcore.rest.CoreAbstBsnsManagerEndpoint;
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectManager;
import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtryArchive;
import org.adorsys.adinvtry.jpa.InvInvtryCstr;
import org.adorsys.adinvtry.jpa.InvInvtryHstry;
import org.adorsys.adinvtry.jpa.InvInvtryHstryArchive;
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.jpa.InvInvtryItemArchive;
import org.adorsys.adinvtry.jpa.InvJob;
import org.adorsys.adinvtry.jpa.InvStep;
import org.adorsys.adinvtry.rest.InvInvtryInjector;
import org.adorsys.adinvtry.rest.InvInvtryItemSearchInput;
import org.adorsys.adinvtry.rest.InvInvtryItemSearchResult;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/inventory")
public class InvInvtryManagerEndpoint extends CoreAbstBsnsManagerEndpoint<InvInvtry, InvInvtryItem, InvInvtryHstry, 
	InvJob, InvStep, InvInvtryCstr>{

	@Inject
	private InvInvtryManager invtryManager;

	@Inject
	private InvInvtryInjector injector;
	
	@Inject
	private InvInvtryArchiveManager archiveManager;

	@Override
	protected CoreAbstBsnsObjectManager<InvInvtry, InvInvtryItem, InvInvtryHstry, InvJob, InvStep, InvInvtryCstr, CoreAbstBsnsObjectSearchInput<InvInvtry>> getBsnsObjManager() {
		return invtryManager;
	}

	@Override
	protected CoreAbstBsnsObjInjector<InvInvtry, InvInvtryItem, InvInvtryHstry, InvJob, InvStep, InvInvtryCstr> getInjector() {
		return injector;
	}

	@Override
	protected CoreAbstArchiveManager<InvInvtry, InvInvtryArchive, InvInvtryItem, InvInvtryItemArchive, InvInvtryHstry, InvInvtryHstryArchive, InvJob, InvStep, InvInvtryCstr> getArchiveManager() {
		return archiveManager;
	}

	@Override
	protected CoreAbstBsnsItemSearchInput<InvInvtryItem> newItemSearchInput() {
		return new InvInvtryItemSearchInput();
	}

	@Override
	protected CoreAbstBsnsItemSearchResult<InvInvtryItem> newItemSearchResult(
			long count, List<InvInvtryItem> resultList,
			CoreAbstBsnsItemSearchInput<InvInvtryItem> itemSearchInput) {
		return new InvInvtryItemSearchResult(count, resultList, itemSearchInput);
	}
}