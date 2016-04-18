package org.adorsys.adcshdwr.rest;

import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesArchive;
import org.adorsys.adcshdwr.repo.CdrDrctSalesArchiveRepo;

public class CdrDrctSalesArchiveEJB extends CoreAbstIdentifiedEJB<CdrDrctSalesArchive> {

	@Inject
	private CdrDrctSalesArchiveRepo repo;
	
	@Override
	protected CoreAbstIdentifRepo<CdrDrctSalesArchive> getRepo() {
		return repo;
	}

}
