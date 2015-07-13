package org.adorsys.adinvtry.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifDataRepo;
import org.adorsys.adcore.rest.CoreAbstEntityCstrEJB;
import org.adorsys.adinvtry.jpa.InvInvtryCstr;
import org.adorsys.adinvtry.repo.InvInvtryCstrRepository;

@Stateless
public class InvInvtryCstrEJB extends CoreAbstEntityCstrEJB<InvInvtryCstr> {

	@Inject
	private InvInvtryCstrRepository repository;
	
	@Override
	public InvInvtryCstr newCsrInstance() {
		return new InvInvtryCstr();
	}

	@Override
	protected CoreAbstIdentifDataRepo<InvInvtryCstr> getRepo() {
		return repository;
	}

}
