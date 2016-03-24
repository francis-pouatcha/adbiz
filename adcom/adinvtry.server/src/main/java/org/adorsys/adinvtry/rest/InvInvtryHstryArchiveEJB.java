package org.adorsys.adinvtry.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifObjectHstryRepo;
import org.adorsys.adcore.rest.CoreAbstArchiveHstryEJB;
import org.adorsys.adinvtry.jpa.InvInvtryHstryArchive;
import org.adorsys.adinvtry.repo.InvInvtryHstryArchiveRepo;

@Stateless
public class InvInvtryHstryArchiveEJB extends CoreAbstArchiveHstryEJB<InvInvtryHstryArchive> {

	@Inject
	private InvInvtryHstryArchiveRepo repo;

	@Override
	protected CoreAbstIdentifObjectHstryRepo<InvInvtryHstryArchive> getRepo() {
		return repo;
	}
}
