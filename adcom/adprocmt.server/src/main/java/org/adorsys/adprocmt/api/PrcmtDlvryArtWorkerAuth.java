package org.adorsys.adprocmt.api;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.adorsys.adcore.auth.AdSystem;
import org.adorsys.adprocmt.jpa.PrcmtDelivery;
import org.adorsys.adprocmt.jpa.PrcmtDlvryArtPrcssng;
import org.adorsys.adprocmt.rest.PrcmtDlvryArtPrcssngEJB;

@Stateless
public class PrcmtDlvryArtWorkerAuth {
	
	@Inject
	private PrcmtDeliveryManager deliveryManager;
	@Inject
	private PrcmtDlvryArtPrcssngEJB artPrcssngEJB;
	@Inject
	private PrcmtDlvryArtProcessor processor;

	@AdSystem
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void processAsSystem(PrcmtDelivery delivery){
		processAsUser(delivery);
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void processAsUser(PrcmtDelivery delivery){
		List<PrcmtDlvryArtPrcssng> list = artPrcssngEJB.findByDlvryNbr(delivery.getDlvryNbr());
		if(!list.isEmpty()){
			for (PrcmtDlvryArtPrcssng artPrcssng : list) {
				processor.process(artPrcssng.getId());
			}
		} else {
			deliveryManager.closeDelivery(delivery);
		}
	}
}
