package org.adorsys.adcost.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcost.jpa.CstActivityCenter;
import org.adorsys.adcost.repo.CstActivityCenterRepo;

@Stateless
public class CstActivityCenterEJB extends CoreAbstIdentifiedEJB<CstActivityCenter> {

	@Inject
	private CstActivityCenterRepo repo;
	
	@Override
	protected CoreAbstIdentifRepo<CstActivityCenter> getRepo() {
		return repo;
	}
}
