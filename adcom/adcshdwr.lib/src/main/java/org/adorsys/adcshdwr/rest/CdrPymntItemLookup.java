package org.adorsys.adcshdwr.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcshdwr.jpa.CdrPymntItem;
import org.adorsys.adcshdwr.repo.CdrPymntItemRepo;

@Stateless
public class CdrPymntItemLookup extends CoreAbstIdentifLookup<CdrPymntItem> {

	@Inject
	private CdrPymntItemRepo repo;
	
	@Override
	protected CoreAbstIdentifRepo<CdrPymntItem> getRepo() {
		return repo;
	}

	@Override
	protected Class<CdrPymntItem> getEntityClass() {
		return CdrPymntItem.class;
	}

}
