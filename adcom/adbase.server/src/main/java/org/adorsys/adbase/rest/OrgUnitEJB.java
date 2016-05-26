package org.adorsys.adbase.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.OrgUnit;
import org.adorsys.adbase.repo.OrgUnitRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;

@Stateless
public class OrgUnitEJB extends CoreAbstIdentifiedEJB<OrgUnit> {
	@Inject
	private OrgUnitRepository repository;

	@Override
	protected CoreAbstIdentifRepo<OrgUnit> getRepo() {
		return repository;
	}
}
