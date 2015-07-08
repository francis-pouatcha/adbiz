package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstEvtRepo;
import org.adorsys.adcore.rest.CoreAbstEvtEJB;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryEvt;
import org.adorsys.adprocmt.repo.PrcmtDeliveryEvtRepository;

@Stateless
public class PrcmtDeliveryEvtEJB extends CoreAbstEvtEJB<PrcmtDeliveryEvt>{

	@Inject
	private PrcmtDeliveryEvtRepository repository;

	@Override
	protected CoreAbstEvtRepo<PrcmtDeliveryEvt> getRepo() {
		return repository;
	}
}
