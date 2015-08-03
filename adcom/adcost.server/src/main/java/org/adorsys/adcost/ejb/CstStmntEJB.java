package org.adorsys.adcost.ejb;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsObjectRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectEJB;
import org.adorsys.adcost.jpa.CstJob;
import org.adorsys.adcost.jpa.CstStep;
import org.adorsys.adcost.jpa.CstStmnt;
import org.adorsys.adcost.jpa.CstStmntCstr;
import org.adorsys.adcost.jpa.CstStmntHstry;
import org.adorsys.adcost.jpa.CstStmntItem;
import org.adorsys.adcost.jpa.CstStmntType;
import org.adorsys.adcost.repo.CstStmntRepo;

@Stateless
public class CstStmntEJB extends CoreAbstBsnsObjectEJB<CstStmnt, CstStmntItem, CstStmntHstry, CstJob, CstStep, CstStmntCstr>
{

	@Inject
	private CstStmntRepo repository;
	@Inject
	private CstStmntInjector injector;

	@Override
	protected CoreAbstBsnsObjInjector<CstStmnt, CstStmntItem, CstStmntHstry, CstJob, CstStep, CstStmntCstr> getInjector() {
		return injector;
	}

	@Override
	protected CoreAbstBsnsObjectRepo<CstStmnt> getBsnsRepo() {
		return repository;
	}

	/*
	 * Override create.
	 * (non-Javadoc)
	 * @see org.adorsys.adcore.rest.CoreAbstBsnsObjectEJB#create(org.adorsys.adcore.jpa.CoreAbstBsnsObject)
	 */
	public CstStmnt create(CstStmnt entity){
		if(entity.getTxType()==null)entity.setTxType(CstStmntType.MISC.name());
		return super.create(entity);
	}
}
