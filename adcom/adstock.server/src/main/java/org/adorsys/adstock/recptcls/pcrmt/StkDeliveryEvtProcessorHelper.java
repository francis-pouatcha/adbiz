package org.adorsys.adstock.recptcls.pcrmt;

import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.BaseBatchEvt;
import org.adorsys.adbase.rest.BaseBatchEvtEJB;
import org.adorsys.adcore.enums.CoreHistoryTypeEnum;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryEvt;
import org.adorsys.adprocmt.rest.PrcmtDeliveryEvtLeaseEJB;
import org.adorsys.adstock.api.ModConstants;

/**
 * Check for the incoming of delivery closed event and process corresponding
 * delivery items.
 * 
 * @author francis
 *
 */
@Stateless
public class StkDeliveryEvtProcessorHelper {

	@Inject
	private PrcmtDeliveryEvtLeaseEJB evtLeaseEJB;
	@Inject
	private BaseBatchEvtEJB batchEvtEJB;

	public void closeEvtLease(String processId, String leaseId,
			PrcmtDeliveryEvt deliveryEvt) {
		
		evtLeaseEJB.close(processId, leaseId);
		BaseBatchEvt batchEvt = new BaseBatchEvt();
		deliveryEvt.copyTo(batchEvt);
		batchEvt.setEvtName(CoreHistoryTypeEnum.COMMITTED.name());
		batchEvt.setId(UUID.randomUUID().toString());
		batchEvt.setEvtModule(ModConstants.MODULE_NAME);
		batchEvt.setEvtKlass(PrcmtDeliveryEvt.class.getSimpleName());
		batchEvtEJB.create(batchEvt);
	}

	public boolean shallProcessEvtLease(PrcmtDeliveryEvt deliveryEvt) {
		List<BaseBatchEvt> found = batchEvtEJB
				.findByEntIdentifAndEvtModuleAndEvtKlassAndEvtName(
						deliveryEvt.getEntIdentif(), ModConstants.MODULE_NAME,
						PrcmtDeliveryEvt.class.getSimpleName(),
						CoreHistoryTypeEnum.COMMITTED.name(), 0, 1);
		return found.isEmpty();
	}
}
