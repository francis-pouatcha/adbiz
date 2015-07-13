package org.adorsys.adstock.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifDataRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adstock.jpa.StkLotStockQty;
import org.adorsys.adstock.repo.StkLotStockQtyRepository;

@Stateless
public class StkLotStockQtyEJB extends CoreAbstIdentifiedEJB<StkLotStockQty>{

	@Inject
	private StkLotStockQtyRepository repository;

	@Override
	protected CoreAbstIdentifDataRepo<StkLotStockQty> getRepo() {
		return repository;
	}
}
