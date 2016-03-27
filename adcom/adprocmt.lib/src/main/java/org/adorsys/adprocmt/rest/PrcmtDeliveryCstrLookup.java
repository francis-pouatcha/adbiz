package org.adorsys.adprocmt.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstEntityCstrRepo;
import org.adorsys.adcore.rest.CoreAbstEntityCstrLookup;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryCstr;
import org.adorsys.adprocmt.repo.PrcmtDeliveryCstrRepository;

@Stateless
public class PrcmtDeliveryCstrLookup extends CoreAbstEntityCstrLookup<PrcmtDeliveryCstr> {

	@Inject
	private PrcmtDeliveryCstrRepository repo;
	
	@Override
	protected CoreAbstEntityCstrRepo<PrcmtDeliveryCstr> getCstrRepo() {
		return repo;
	}

	@Override
	protected Class<PrcmtDeliveryCstr> getEntityClass() {
		return PrcmtDeliveryCstr.class;
	}

}
