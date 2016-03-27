package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstEntityCstrRepo;
import org.adorsys.adcore.rest.CoreAbstEntityCstrLookup;
import org.adorsys.adprocmt.jpa.PrcmtProcOrderCstr;
import org.adorsys.adprocmt.repo.PrcmtProcOrderCstrRepository;

@Stateless
public class PrcmtProcOrderCstrLookup extends CoreAbstEntityCstrLookup<PrcmtProcOrderCstr> {

	@Inject
	private PrcmtProcOrderCstrRepository repo;
	
	@Override
	protected CoreAbstEntityCstrRepo<PrcmtProcOrderCstr> getCstrRepo() {
		return repo;
	}

	@Override
	protected Class<PrcmtProcOrderCstr> getEntityClass() {
		return PrcmtProcOrderCstr.class;
	}

}
