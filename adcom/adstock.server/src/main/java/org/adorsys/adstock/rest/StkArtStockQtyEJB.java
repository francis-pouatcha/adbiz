package org.adorsys.adstock.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adstock.jpa.StkArtStockQty;
import org.adorsys.adstock.repo.StkArtStockQtyRepository;

@Stateless
public class StkArtStockQtyEJB extends CoreAbstIdentifiedEJB<StkArtStockQty>{

	@Inject
	private StkArtStockQtyRepository repository;

	@Override
	protected CoreAbstIdentifRepo<StkArtStockQty> getRepo() {
		return repository;
	}
}
