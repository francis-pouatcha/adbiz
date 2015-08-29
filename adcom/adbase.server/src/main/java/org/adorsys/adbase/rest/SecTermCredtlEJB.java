package org.adorsys.adbase.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.SecTermCredtl;
import org.adorsys.adbase.repo.SecTermCredtlRepository;
import org.adorsys.adcore.repo.CoreAbstEntityRepo;
import org.adorsys.adcore.rest.CoreAbstEntityEJB;

@Stateless
public class SecTermCredtlEJB extends CoreAbstEntityEJB<SecTermCredtl> {

	@Inject
	private SecTermCredtlRepository repo;

	@Override
	protected CoreAbstEntityRepo<SecTermCredtl> getRepo() {
		return repo;
	}
}
