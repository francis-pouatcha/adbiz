package org.adorsys.adstock.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedEJB;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.adorsys.adstock.repo.StkArticleLot2StrgSctnRepository;

@Stateless
public class StkArticleLot2StrgSctnEJB extends
		CoreAbstIdentifiedEJB<StkArticleLot2StrgSctn> {
	@Inject
	private StkArticleLot2StrgSctnRepository repository;

	@Override
	protected CoreAbstIdentifRepo<StkArticleLot2StrgSctn> getRepo() {
		return repository;
	}
}
