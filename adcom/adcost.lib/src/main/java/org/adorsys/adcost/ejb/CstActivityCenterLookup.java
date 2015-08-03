package org.adorsys.adcost.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcost.jpa.CstActivityCenter;
import org.adorsys.adcost.repo.CstActivityCenterRepo;

@Stateless
public class CstActivityCenterLookup extends
		CoreAbstIdentifLookup<CstActivityCenter> {
	@Inject
	private CstActivityCenterRepo repo;
	
	@Override
	protected CoreAbstIdentifRepo<CstActivityCenter> getRepo() {
		return repo;
	}

	@Override
	protected Class<CstActivityCenter> getEntityClass() {
		return CstActivityCenter.class;
	}
}
