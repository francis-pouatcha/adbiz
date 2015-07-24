package org.adorsys.adstock.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adstock.jpa.StkLotStockQty;
import org.adorsys.adstock.repo.StkLotStockQtyRepository;

@Stateless
public class StkLotStockQtyEJB extends CoreAbstIdentifiedEJB<StkLotStockQty>{

	@Inject
	private StkLotStockQtyRepository repository;

	@Override
	protected CoreAbstIdentifRepo<StkLotStockQty> getRepo() {
		return repository;
	}
}
