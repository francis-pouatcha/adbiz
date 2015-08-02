package org.adorsys.adcatal.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArtManufSupp;
import org.adorsys.adcatal.repo.CatalArtManufSuppRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;

@Stateless
public class CatalArtManufSuppLookup extends
		CoreAbstIdentifLookup<CatalArtManufSupp> {

	@Inject
	private CatalArtManufSuppRepository repository;

	@Override
	protected CoreAbstIdentifRepo<CatalArtManufSupp> getRepo() {
		return repository;
	}

	@Override
	protected Class<CatalArtManufSupp> getEntityClass() {
		return CatalArtManufSupp.class;
	}
}
