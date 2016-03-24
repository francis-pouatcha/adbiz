package org.adorsys.adinvtry.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adinvtry.jpa.InvInvtryArchive;
import org.adorsys.adinvtry.repo.InvInvtryArchiveRepo;

@Stateless
public class InvInvtryArchiveLookup extends CoreAbstIdentifLookup<InvInvtryArchive>
{

	@Inject
	private InvInvtryArchiveRepo repository;

	@Override
	protected InvInvtryArchiveRepo getRepo() {
		return repository;
	}

	@Override
	protected Class<InvInvtryArchive> getEntityClass() {
		return InvInvtryArchive.class;
	}

}
