package org.adorsys.adcshdwr.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcshdwr.jpa.CdrCstmrVchr;
import org.adorsys.adcshdwr.repo.CdrCstmrVchrRepository;

@Stateless
public class CdrCstmrVchrLookup extends CoreAbstIdentifLookup<CdrCstmrVchr> {

	@Inject
	private CdrCstmrVchrRepository repo;

	@Override
	protected Class<CdrCstmrVchr> getEntityClass() {
		return CdrCstmrVchr.class;
	}

	@Override
	protected CoreAbstIdentifRepo<CdrCstmrVchr> getRepo() {
		return repo;
	}

}
