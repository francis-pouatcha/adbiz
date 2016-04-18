package org.adorsys.adbase.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.OrgUnit;
import org.adorsys.adbase.repo.OrgUnitRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;

@Stateless
public class OrgUnitLookup extends CoreAbstIdentifLookup<OrgUnit> {
	@Inject
	private OrgUnitRepository repository;

	@Override
	protected CoreAbstIdentifRepo<OrgUnit> getRepo() {
		return repository;
	}

	@Override
	protected Class<OrgUnit> getEntityClass() {
		return OrgUnit.class;
	}
	
	public void findTenant(String realm){
		
	}
}
