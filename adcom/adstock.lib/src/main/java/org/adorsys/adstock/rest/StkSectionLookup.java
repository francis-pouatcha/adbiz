package org.adorsys.adstock.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adstock.jpa.StkSection;
import org.adorsys.adstock.repo.StkSectionRepository;

@Stateless
public class StkSectionLookup extends CoreAbstIdentifLookup<StkSection> {

	@Inject
	private StkSectionRepository repository;

	@Override
	protected CoreAbstIdentifRepo<StkSection> getRepo() {
		return repository;
	}

	@Override
	protected Class<StkSection> getEntityClass() {
		return StkSection.class;
	}
}
