package org.adorsys.adstock.rest;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.adorsys.adstock.repo.StkArticleLot2StrgSctnRepository;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class StkArticleLot2StrgSctnLookup extends
		CoreAbstIdentifLookup<StkArticleLot2StrgSctn> {

	@Inject
	private StkArticleLot2StrgSctnRepository repository;

	@Override
	protected CoreAbstIdentifRepo<StkArticleLot2StrgSctn> getRepo() {
		return repository;
	}

	public StkArticleLot2StrgSctn findBySectionAndLotPic(String strgSection, String lotPic){
		String primaryKey = StkArticleLot2StrgSctn.toLotPicAndDectionKey(lotPic, strgSection);
		return repository.findBy(primaryKey);
	}

	@Override
	protected Class<StkArticleLot2StrgSctn> getEntityClass() {
		return StkArticleLot2StrgSctn.class;
	}
}
