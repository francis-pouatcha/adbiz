package org.adorsys.adstock.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsObjHstryRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectHstryLookup;
import org.adorsys.adstock.jpa.StkArticleLotHstry;
import org.adorsys.adstock.repo.StkArticleLotHstryRepo;

@Stateless
public class StkArticleLotHstryLookup  extends CoreAbstBsnsObjectHstryLookup<StkArticleLotHstry>{

	@Inject
	private StkArticleLotHstryRepo repo;

	@Override
	protected CoreAbstBsnsObjHstryRepo<StkArticleLotHstry> getRepo() {
		return repo;
	}

}
