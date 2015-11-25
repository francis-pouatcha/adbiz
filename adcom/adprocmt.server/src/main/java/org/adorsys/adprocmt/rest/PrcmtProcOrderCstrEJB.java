package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstEntityCstrEJB;
import org.adorsys.adprocmt.jpa.PrcmtProcOrderCstr;
import org.adorsys.adprocmt.repo.PrcmtProcOrderCstrRepository;

@Stateless
public class PrcmtProcOrderCstrEJB extends CoreAbstEntityCstrEJB<PrcmtProcOrderCstr>{

	@Inject
	private PrcmtProcOrderCstrRepository repo;
	
	@Override
	public PrcmtProcOrderCstr newCsrInstance() {
		return new PrcmtProcOrderCstr();
	}

	@Override
	protected CoreAbstIdentifRepo<PrcmtProcOrderCstr> getRepo() {
		return repo;
	}

}
