package org.adorsys.adcatal.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalProdFmly;
import org.adorsys.adcatal.repo.CatalProdFmlyRepo;
import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;

@Stateless
public class CatalProdFmlyEJB extends
		CoreAbstIdentifiedEJB<CatalProdFmly> {

	@Inject
	private CatalProdFmlyRepo repository;

	@Override
	protected CoreAbstIdentifRepo<CatalProdFmly> getRepo() {
		return repository;
	}
	
}
