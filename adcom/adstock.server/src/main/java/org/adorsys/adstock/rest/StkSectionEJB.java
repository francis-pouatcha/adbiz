package org.adorsys.adstock.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adstock.jpa.StkSection;
import org.adorsys.adstock.repo.StkSectionRepository;

@Stateless
public class StkSectionEJB extends CoreAbstIdentifiedEJB<StkSection> {

	@Inject
	private StkSectionRepository repository;

	@Override
	protected CoreAbstIdentifRepo<StkSection> getRepo() {
		return repository;
	}
}
