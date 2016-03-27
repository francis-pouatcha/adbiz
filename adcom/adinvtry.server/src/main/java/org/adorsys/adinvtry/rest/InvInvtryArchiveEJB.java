package org.adorsys.adinvtry.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adinvtry.jpa.InvInvtryArchive;
import org.adorsys.adinvtry.repo.InvInvtryArchiveRepo;

@Stateless
public class InvInvtryArchiveEJB extends CoreAbstIdentifiedEJB<InvInvtryArchive>
{

	@Inject
	private InvInvtryArchiveRepo repository;

	@Override
	protected CoreAbstIdentifRepo<InvInvtryArchive> getRepo() {
		return repository;
	}

}
