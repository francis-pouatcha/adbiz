package org.adorsys.adinvtry.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsObjectRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectLookup;
import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.repo.InvInvtryRepository;

@Stateless
public class InvInvtryLookup extends CoreAbstBsnsObjectLookup<InvInvtry>
{

	@Inject
	private InvInvtryRepository repository;

	@Override
	protected CoreAbstBsnsObjectRepo<InvInvtry> getBsnsRepo() {
		return repository;
	}

	@Override
	protected Class<InvInvtry> getEntityClass() {
		return InvInvtry.class;
	}
}
