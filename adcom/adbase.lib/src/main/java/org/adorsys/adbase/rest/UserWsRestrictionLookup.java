package org.adorsys.adbase.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.UserWsRestriction;
import org.adorsys.adbase.repo.UserWsRestrictionRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;

@Stateless
public class UserWsRestrictionLookup extends
		CoreAbstIdentifLookup<UserWsRestriction> {

	@Inject
	private UserWsRestrictionRepository repository;

	@Override
	protected CoreAbstIdentifRepo<UserWsRestriction> getRepo() {
		return repository;
	}

	@Override
	protected Class<UserWsRestriction> getEntityClass() {
		return UserWsRestriction.class;
	}
}
