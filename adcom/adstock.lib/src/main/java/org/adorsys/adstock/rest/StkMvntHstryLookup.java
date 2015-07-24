package org.adorsys.adstock.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstBsnsObjHstryRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectHstryLookup;
import org.adorsys.adstock.jpa.StkMvntHstry;
import org.adorsys.adstock.repo.StkMvntHstryRepo;

@Stateless
public class StkMvntHstryLookup  extends CoreAbstBsnsObjectHstryLookup<StkMvntHstry>{

	@Inject
	private StkMvntHstryRepo repo;

	@Override
	protected CoreAbstBsnsObjHstryRepo<StkMvntHstry> getRepo() {
		return repo;
	}

}
