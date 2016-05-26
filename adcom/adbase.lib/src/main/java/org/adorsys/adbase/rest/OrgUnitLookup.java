package org.adorsys.adbase.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.OrgUnit;
import org.adorsys.adbase.repo.OrgUnitRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.apache.deltaspike.data.api.QueryResult;

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
	
	public OrgUnit findTenant(String realm){
		QueryResult<OrgUnit> tenant = repository.findByRealmAndTenant(realm, Boolean.TRUE);
		List<OrgUnit> resultList = tenant.firstResult(0).maxResults(1).getResultList();
		if(resultList.isEmpty()) return null;
		return resultList.iterator().next();
	}
}
