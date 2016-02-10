package org.adorsys.adstock.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adstock.jpa.StkArt2Section;
import org.adorsys.adstock.repo.StkArt2SectionRepository;

@Stateless
public class StkArt2SectionEJB extends CoreAbstIdentifiedEJB<StkArt2Section> {

	@Inject
	private StkArt2SectionRepository repository;

	@Override
	protected CoreAbstIdentifRepo<StkArt2Section> getRepo() {
		return repository;
	}	
}
