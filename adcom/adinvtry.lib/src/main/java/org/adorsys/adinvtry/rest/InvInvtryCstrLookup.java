package org.adorsys.adinvtry.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstEntityCstrRepo;
import org.adorsys.adcore.rest.CoreAbstEntityCstrLookup;
import org.adorsys.adinvtry.jpa.InvInvtryCstr;
import org.adorsys.adinvtry.repo.InvInvtryCstrRepository;

@Stateless
public class InvInvtryCstrLookup extends CoreAbstEntityCstrLookup<InvInvtryCstr> {

	@Inject
	private InvInvtryCstrRepository repository;

	@Override
	protected CoreAbstEntityCstrRepo<InvInvtryCstr> getCstrRepo() {
		return repository;
	}

	@Override
	protected Class<InvInvtryCstr> getEntityClass() {
		return InvInvtryCstr.class;
	}

}
