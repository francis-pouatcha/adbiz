package org.adorsys.adstock.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adstock.jpa.StkArtLotMgmnt;
import org.adorsys.adstock.repo.StkArtLotMgmntRepo;

@Stateless
public class StkArtLotMgmntEJB  extends CoreAbstIdentifiedEJB<StkArtLotMgmnt> {

	@Inject
	private StkArtLotMgmntRepo repo;

	@Override
	protected CoreAbstIdentifRepo<StkArtLotMgmnt> getRepo() {
		return repo;
	}	
}
