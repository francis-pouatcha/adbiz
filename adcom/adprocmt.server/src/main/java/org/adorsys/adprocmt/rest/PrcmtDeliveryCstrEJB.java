package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstEntityCstrEJB;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryCstr;
import org.adorsys.adprocmt.repo.PrcmtDeliveryCstrRepository;

@Stateless
public class PrcmtDeliveryCstrEJB extends CoreAbstEntityCstrEJB<PrcmtDeliveryCstr>{

	@Inject
	private PrcmtDeliveryCstrRepository repo;
	
	@Override
	public PrcmtDeliveryCstr newCsrInstance() {
		return new PrcmtDeliveryCstr();
	}

	@Override
	protected CoreAbstIdentifRepo<PrcmtDeliveryCstr> getRepo() {
		return repo;
	}

}
