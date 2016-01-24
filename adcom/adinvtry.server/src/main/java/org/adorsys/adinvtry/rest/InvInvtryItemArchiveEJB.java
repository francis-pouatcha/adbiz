package org.adorsys.adinvtry.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adinvtry.jpa.InvInvtryItemArchive;
import org.adorsys.adinvtry.repo.InvInvtryItemArchiveRepo;

@Stateless
public class InvInvtryItemArchiveEJB extends CoreAbstIdentifiedEJB<InvInvtryItemArchive>
{

	@Inject
	private InvInvtryItemArchiveRepo repository;

	@Override
	protected CoreAbstIdentifRepo<InvInvtryItemArchive> getRepo() {
		return repository;
	}

}
