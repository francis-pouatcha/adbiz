package org.adorsys.adstock.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adstock.jpa.StkArt2Section;
import org.adorsys.adstock.repo.StkArt2SectionRepository;

@Stateless
public class StkArt2SectionLookup extends
		CoreAbstIdentifLookup<StkArt2Section> {

	@Inject
	private StkArt2SectionRepository repository;

	@Override
	protected CoreAbstIdentifRepo<StkArt2Section> getRepo() {
		return repository;
	}

	@Override
	protected Class<StkArt2Section> getEntityClass() {
		return StkArt2Section.class;
	}
}
