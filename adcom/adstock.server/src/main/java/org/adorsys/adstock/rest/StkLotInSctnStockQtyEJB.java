package org.adorsys.adstock.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adstock.jpa.StkLotInSctnStockQty;
import org.adorsys.adstock.repo.StkLotInSctnStockQtyRepository;

@Stateless
public class StkLotInSctnStockQtyEJB extends CoreAbstIdentifiedEJB<StkLotInSctnStockQty>{

	@Inject
	private StkLotInSctnStockQtyRepository repository;

	@Override
	protected CoreAbstIdentifRepo<StkLotInSctnStockQty> getRepo() {
		return repository;
	}
}
