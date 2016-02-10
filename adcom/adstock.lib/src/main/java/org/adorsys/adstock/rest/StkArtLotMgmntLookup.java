package org.adorsys.adstock.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adstock.jpa.StkArtLotMgmnt;
import org.adorsys.adstock.repo.StkArtLotMgmntRepo;

@Stateless
public class StkArtLotMgmntLookup extends
		CoreAbstIdentifLookup<StkArtLotMgmnt> {

	@Inject
	private StkArtLotMgmntRepo repo;

	@Override
	protected CoreAbstIdentifRepo<StkArtLotMgmnt> getRepo() {
		return repo;
	}

	@Override
	protected Class<StkArtLotMgmnt> getEntityClass() {
		return StkArtLotMgmnt.class;
	}
	
	public StkArtLotMgmnt findCurrentByArtPic(String artPic){
		return repo.findByCntnrIdentif(artPic).orderDesc("valueDt").firstResult(0).maxResults(1).getOptionalResult();
	}

}
