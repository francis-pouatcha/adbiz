package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsObjHstryRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectHstryLookup;
import org.adorsys.adprocmt.jpa.PrcmtProcOrderHstry;
import org.adorsys.adprocmt.repo.PrcmtProcOrderHstryRepository;

@Stateless
public class PrcmtProcOrderHstryLookup extends CoreAbstBsnsObjectHstryLookup<PrcmtProcOrderHstry>{

	@Inject
	private PrcmtProcOrderHstryRepository repository;

	@Override
	protected CoreAbstBsnsObjHstryRepo<PrcmtProcOrderHstry> getRepo() {
		return repository;
	}
}
