package org.adorsys.adinvtry.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifObjectHstryRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedHstryLookup;
import org.adorsys.adinvtry.jpa.InvInvtryHstryArchive;
import org.adorsys.adinvtry.repo.InvInvtryHstryArchiveRepo;

@Stateless
public class InvInvtryHstryArchiveLookup extends CoreAbstIdentifiedHstryLookup<InvInvtryHstryArchive>{

	@Inject
	private InvInvtryHstryArchiveRepo repository;

	@Override
	protected CoreAbstIdentifObjectHstryRepo<InvInvtryHstryArchive> getRepo() {
		return repository;
	}

}
