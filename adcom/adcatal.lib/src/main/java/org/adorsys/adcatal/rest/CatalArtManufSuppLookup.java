package org.adorsys.adcatal.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArtManufSupp;
import org.adorsys.adcatal.repo.CatalArtManufSuppRepository;
import org.adorsys.adcore.repo.CoreAbstIdentifDataRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedLookup;

@Stateless
public class CatalArtManufSuppLookup extends
		CoreAbstIdentifiedLookup<CatalArtManufSupp> {

	@Inject
	private CatalArtManufSuppRepository repository;

	@Override
	protected CoreAbstIdentifDataRepo<CatalArtManufSupp> getRepo() {
		return repository;
	}

}
