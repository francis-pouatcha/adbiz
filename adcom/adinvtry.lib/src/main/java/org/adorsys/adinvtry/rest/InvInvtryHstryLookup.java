package org.adorsys.adinvtry.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsObjHstryRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectHstryLookup;
import org.adorsys.adinvtry.jpa.InvInvtryHstry;
import org.adorsys.adinvtry.repo.InvInvtryHstryRepository;

@Stateless
public class InvInvtryHstryLookup extends CoreAbstBsnsObjectHstryLookup<InvInvtryHstry>{

	@Inject
	private InvInvtryHstryRepository repository;

	@Override
	protected CoreAbstBsnsObjHstryRepo<InvInvtryHstry> getRepo() {
		return repository;
	}

}
