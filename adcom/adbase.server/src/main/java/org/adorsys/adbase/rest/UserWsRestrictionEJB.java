package org.adorsys.adbase.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.UserWsRestriction;
import org.adorsys.adbase.repo.UserWsRestrictionRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;

@Stateless
public class UserWsRestrictionEJB extends
		CoreAbstIdentifiedEJB<UserWsRestriction> {

	@Inject
	private UserWsRestrictionRepository repo;

	@Override
	protected CoreAbstIdentifRepo<UserWsRestriction> getRepo() {
		return repo;
	}

}
