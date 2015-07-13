package org.adorsys.adstock.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifDataRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifiedLookup;
import org.adorsys.adstock.jpa.StkLotStockQty;
import org.adorsys.adstock.repo.StkLotStockQtyRepository;

@Stateless
public class StkLotStockQtyLookup extends
		CoreAbstIdentifiedLookup<StkLotStockQty> {

	@Inject
	private StkLotStockQtyRepository repository;

	@Override
	protected CoreAbstIdentifDataRepo<StkLotStockQty> getRepo() {
		return repository;
	}

	public StkLotStockQty findBase(String artPic, String lotPic, String section) {
		List<StkLotStockQty> rl = repository
				.findByArtPicAndLotPicAndSectionAndCnsldtd(artPic, lotPic,
						section, Boolean.TRUE).getResultList();
		if (!rl.isEmpty()) {
			return rl.iterator().next();
		}
		return null;
	}

	public List<StkLotStockQty> findByArtPicAndLotPicAndSectionAndSeqNbr(
			String artPic, String lotPic, String section, Integer seqNbr) {
		return repository
				.findByArtPicAndLotPicAndSectionAndSeqNbr(artPic, lotPic,
						section, seqNbr).orderDesc("seqNbr").getResultList();
	}
}
