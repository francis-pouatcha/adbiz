package org.adorsys.adcshdwr.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifObjectHstryRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedHstryLookup;
import org.adorsys.adcshdwr.jpa.CdrCstmrVchrHstry;
import org.adorsys.adcshdwr.repo.CdrCstmrVchrHstryHstryRepo;

@Stateless
public class CdrCstmrVchrHstryLookup extends CoreAbstIdentifiedHstryLookup<CdrCstmrVchrHstry> {

	@Inject
	private CdrCstmrVchrHstryHstryRepo repo;

	@Override
	protected CoreAbstIdentifObjectHstryRepo<CdrCstmrVchrHstry> getRepo() {
		return repo;
	}

}
