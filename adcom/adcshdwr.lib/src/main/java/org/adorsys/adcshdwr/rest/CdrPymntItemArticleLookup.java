package org.adorsys.adcshdwr.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcshdwr.jpa.CdrPymntItemArchive;
import org.adorsys.adcshdwr.repo.CdrPymntItemArchiveRepo;

@Stateless
public class CdrPymntItemArticleLookup extends CoreAbstIdentifLookup<CdrPymntItemArchive> {

	@Inject
	private CdrPymntItemArchiveRepo repo;
	
	@Override
	protected CoreAbstIdentifRepo<CdrPymntItemArchive> getRepo() {
		return repo;
	}

	@Override
	protected Class<CdrPymntItemArchive> getEntityClass() {
		return CdrPymntItemArchive.class;
	}

}
