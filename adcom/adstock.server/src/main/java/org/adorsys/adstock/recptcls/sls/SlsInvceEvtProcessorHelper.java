package org.adorsys.adstock.recptcls.sls;

import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.BaseBatchEvt;
import org.adorsys.adbase.rest.BaseBatchEvtEJB;
import org.adorsys.adcore.enums.CoreHistoryTypeEnum;
import org.adorsys.adinvtry.jpa.InvInvtryEvt;
import org.adorsys.adsales.jpa.SlsInvceEvt;
import org.adorsys.adsales.rest.SlsInvceEvtLeaseEJB;
import org.adorsys.adstock.api.ModConstants;

/**
 * Check for the incoming of inventory closed event and process corresponding
 * inventory items.
 * 
 * @author francis
 *
 */
@Stateless
public class SlsInvceEvtProcessorHelper {

	@Inject
	private BaseBatchEvtEJB batchEvtEJB;

	@Inject
	private SlsInvceEvtLeaseEJB evtLeaseEJB;

	public void closeEvtLease(String processId, String leaseId,
			SlsInvceEvt evt) {
		evtLeaseEJB.close(processId, leaseId);
		BaseBatchEvt batchEvt = new BaseBatchEvt();
		evt.copyTo(batchEvt);
		batchEvt.setEvtName(CoreHistoryTypeEnum.COMMITTED.name());
		batchEvt.setId(UUID.randomUUID().toString());
		batchEvt.setEvtModule(ModConstants.MODULE_NAME);
		batchEvt.setEvtKlass(InvInvtryEvt.class.getSimpleName());
		batchEvtEJB.create(batchEvt);
	}

	public boolean shallProcessEvtLease(SlsInvceEvt evt) {
		List<BaseBatchEvt> found = batchEvtEJB
				.findByEntIdentifAndEvtModuleAndEvtKlassAndEvtName(
						evt.getEntIdentif(), ModConstants.MODULE_NAME,
						SlsInvceEvt.class.getSimpleName(),
						CoreHistoryTypeEnum.COMMITTED.name(), 0, 1);
		return found.isEmpty();
	}
}
