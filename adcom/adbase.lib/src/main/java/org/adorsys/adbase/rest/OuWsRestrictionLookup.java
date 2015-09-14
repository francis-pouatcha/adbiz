package org.adorsys.adbase.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.OuWsRestriction;
import org.adorsys.adbase.repo.OuWsRestrictionRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;

@Stateless
public class OuWsRestrictionLookup extends
		CoreAbstIdentifLookup<OuWsRestriction> {

	@Inject
	private OuWsRestrictionRepository repository;

	@Override
	protected CoreAbstIdentifRepo<OuWsRestriction> getRepo() {
		return repository;
	}

	@Override
	protected Class<OuWsRestriction> getEntityClass() {
		return OuWsRestriction.class;
	}
}
