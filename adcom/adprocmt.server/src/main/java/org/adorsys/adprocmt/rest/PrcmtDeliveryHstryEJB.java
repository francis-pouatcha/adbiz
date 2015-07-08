package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsObjHstryRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectHstryEJB;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryHstry;
import org.adorsys.adprocmt.repo.PrcmtDeliveryHstryRepository;

@Stateless
public class PrcmtDeliveryHstryEJB extends CoreAbstBsnsObjectHstryEJB<PrcmtDeliveryHstry>{

	@Inject
	private PrcmtDeliveryHstryRepository repository;
	
	@Inject
	@PrcmtDeliveryHstryEvent
	private Event<PrcmtDeliveryHstry> deliveryHstryEvent;

//	public PrcmtDeliveryHstry create(PrcmtDeliveryHstry entity) {
//		PrcmtDeliveryHstry deliveryHstry = repository.save(attach(entity));
//		if(CoreHistoryTypeEnum.CLOSING.name().equals(deliveryHstry.getHstryType())){
//			deliveryClosingEvent.fire(deliveryHstry);
//		} else if (CoreHistoryTypeEnum.CLOSED.name().equals(deliveryHstry.getHstryType())){
//			deliveryClosedEvent.fire(deliveryHstry);
//		}
//		return deliveryHstry;
//	}

	@Override
	protected CoreAbstBsnsObjHstryRepo<PrcmtDeliveryHstry> getRepo() {
		return repository;
	}

	@Override
	protected void fireEvent(PrcmtDeliveryHstry hstry) {
		deliveryHstryEvent.fire(hstry);
	}

	@Override
	protected PrcmtDeliveryHstry newHstryObj() {
		return new PrcmtDeliveryHstry();
	}
}
