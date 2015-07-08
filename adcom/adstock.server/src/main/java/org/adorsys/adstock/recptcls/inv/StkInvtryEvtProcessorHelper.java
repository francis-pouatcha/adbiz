package org.adorsys.adstock.recptcls.inv;

import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.BaseBatchEvt;
import org.adorsys.adbase.rest.BaseBatchEvtEJB;
import org.adorsys.adcore.enums.CoreHistoryTypeEnum;
import org.adorsys.adinvtry.jpa.InvInvtryEvt;
import org.adorsys.adinvtry.rest.InvInvtryEvtLeaseEJB;
import org.adorsys.adstock.api.ModConstants;

/**
 * Check for the incoming of inventory closed event and process corresponding
 * inventory items.
 * 
 * @author francis
 *
 */
@Stateless
public class StkInvtryEvtProcessorHelper {

	@Inject
	private BaseBatchEvtEJB batchEvtEJB;

	@Inject
	private InvInvtryEvtLeaseEJB evtLeaseEJB;

	public void closeEvtLease(String processId, String leaseId,
			InvInvtryEvt invtryEvt) {
		evtLeaseEJB.close(processId, leaseId);
		BaseBatchEvt batchEvt = new BaseBatchEvt();
		invtryEvt.copyTo(batchEvt);
		batchEvt.setEvtName(CoreHistoryTypeEnum.COMMITTED.name());
		batchEvt.setId(UUID.randomUUID().toString());
		batchEvt.setEvtModule(ModConstants.MODULE_NAME);
		batchEvt.setEvtKlass(InvInvtryEvt.class.getSimpleName());
		batchEvtEJB.create(batchEvt);
	}

	public boolean shallProcessEvtLease(InvInvtryEvt invtryEvt) {
		List<BaseBatchEvt> found = batchEvtEJB
				.findByEntIdentifAndEvtModuleAndEvtKlassAndEvtName(
						invtryEvt.getEntIdentif(), ModConstants.MODULE_NAME,
						InvInvtryEvt.class.getSimpleName(),
						CoreHistoryTypeEnum.COMMITTED.name(), 0, 1);
		return found.isEmpty();
	}
}
