package org.adorsys.adcshdwr.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcshdwr.jpa.CdrPymnt;
import org.adorsys.adcshdwr.repo.CdrPymntRepo;

@Stateless
public class CdrPymntLookup extends CoreAbstIdentifLookup<CdrPymnt> {

	@Inject
	private CdrPymntRepo repo;

	@Override
	protected Class<CdrPymnt> getEntityClass() {
		return CdrPymnt.class;
	}

	@Override
	protected CoreAbstIdentifRepo<CdrPymnt> getRepo() {
		return repo;
	}

}
