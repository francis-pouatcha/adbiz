package org.adorsys.adstock.rest;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.jpa.CoreAbstBsnsObject;
import org.adorsys.adcore.jpa.CoreAbstEntityCstr;
import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsItemEJB;
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.jpa.StkArticleLotHstry;
import org.adorsys.adstock.jpa.StkArticleLotJob;
import org.adorsys.adstock.jpa.StkArticleLotStep;
import org.adorsys.adstock.repo.StkArticleLotRepository;

@Stateless
public class StkArticleLotEJB extends CoreAbstBsnsItemEJB<CoreAbstBsnsObject, StkArticleLot, StkArticleLotHstry, StkArticleLotJob, StkArticleLotStep, CoreAbstEntityCstr> {

	@Inject
	private StkArticleLotRepository repo;
	
	@EJB
	private StkArticleLotInjector injector;
	
	@Override
	protected CoreAbstBsnsObjInjector<CoreAbstBsnsObject, StkArticleLot, StkArticleLotHstry, StkArticleLotJob, StkArticleLotStep, CoreAbstEntityCstr> getInjector() {
		return injector;
	}

	@Override
	protected CoreAbstBsnsItemRepo<StkArticleLot> getBsnsRepo() {
		return repo;
	}
}
