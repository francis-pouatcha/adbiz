package org.adorsys.adstock.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifDataRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedLookup;
import org.adorsys.adstock.jpa.StkSection;
import org.adorsys.adstock.repo.StkSectionRepository;

@Stateless
public class StkSectionLookup extends CoreAbstIdentifiedLookup<StkSection> {

	@Inject
	private StkSectionRepository repository;

	@Override
	protected CoreAbstIdentifDataRepo<StkSection> getRepo() {
		return repository;
	}

	public Long countByParentCode(String parentCode){
		return repository.findByParentCode(parentCode).count();
	}
	public List<StkSection> findByParentCode(String parentCode, int start, int max){
		return repository.findByParentCode(parentCode).firstResult(start).maxResults(max).getResultList();
	}
}
