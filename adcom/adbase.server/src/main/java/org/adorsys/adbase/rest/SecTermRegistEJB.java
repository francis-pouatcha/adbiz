package org.adorsys.adbase.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.SecTermRegist;
import org.adorsys.adbase.repo.SecTermRegistRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;

@Stateless
public class SecTermRegistEJB extends CoreAbstIdentifiedEJB<SecTermRegist> {

	@Inject
	private SecTermRegistRepository repository;

	@Override
	protected CoreAbstIdentifRepo<SecTermRegist> getRepo() {
		return repository;
	}
}
