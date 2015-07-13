package org.adorsys.adstock.rest;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifDataRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedLookup;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.adorsys.adstock.repo.StkArticleLot2StrgSctnRepository;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class StkArticleLot2StrgSctnLookup extends
		CoreAbstIdentifiedLookup<StkArticleLot2StrgSctn> {

	@Inject
	private StkArticleLot2StrgSctnRepository repository;

	@Override
	protected CoreAbstIdentifDataRepo<StkArticleLot2StrgSctn> getRepo() {
		return repository;
	}

	public StkArticleLot2StrgSctn findByStrgSectionAndLotPic(String strgSection, String lotPic){
		String primaryKey = StkArticleLot2StrgSctn.toId(lotPic, strgSection);
		return repository.findBy(primaryKey);
	}
}
