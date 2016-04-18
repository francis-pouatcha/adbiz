package org.adorsys.adcshdwr.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcshdwr.jpa.CdrPymntArchive;
import org.adorsys.adcshdwr.repo.CdrPymntArchiveRepo;

@Stateless
public class CdrPymntArchiveLookup extends CoreAbstIdentifLookup<CdrPymntArchive> {

	@Inject
	private CdrPymntArchiveRepo repo;

	@Override
	protected Class<CdrPymntArchive> getEntityClass() {
		return CdrPymntArchive.class;
	}

	@Override
	protected CoreAbstIdentifRepo<CdrPymntArchive> getRepo() {
		return repo;
	}

}
