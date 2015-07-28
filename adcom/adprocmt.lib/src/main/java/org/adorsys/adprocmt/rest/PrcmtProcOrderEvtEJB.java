package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstEvtRepo;
import org.adorsys.adcore.rest.CoreAbstEvtEJB;
import org.adorsys.adprocmt.jpa.PrcmtProcOrderEvt;
import org.adorsys.adprocmt.repo.PrcmtProcOrderEvtRepository;

@Stateless
public class PrcmtProcOrderEvtEJB extends CoreAbstEvtEJB<PrcmtProcOrderEvt>{

	@Inject
	private PrcmtProcOrderEvtRepository repository;

	@Override
	protected CoreAbstEvtRepo<PrcmtProcOrderEvt> getRepo() {
		return repository;
	}
}
