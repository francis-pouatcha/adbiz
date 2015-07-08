package org.adorsys.adinvtry.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.adorsys.adcore.repo.CoreAbstBsnsObjectRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectLookup;
import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.repo.InvInvtryRepository;

@Stateless
public class InvInvtryLookup extends CoreAbstBsnsObjectLookup<InvInvtry>
{

	@Inject
	private InvInvtryRepository repository;
	
	@Inject
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Override
	protected CoreAbstBsnsObjectRepo<InvInvtry> getBsnsRepo() {
		return repository;
	}

	@Override
	protected Class<InvInvtry> getBsnsObjClass() {
		return InvInvtry.class;
	}

}
