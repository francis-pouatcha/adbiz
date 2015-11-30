package org.adorsys.adstock.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adstock.jpa.StkLotInSctnStockQty;
import org.adorsys.adstock.repo.StkLotInSctnStockQtyRepository;

@Stateless
public class StkLotInSctnStockQtyLookup extends
		CoreAbstIdentifLookup<StkLotInSctnStockQty> {

	@Inject
	private StkLotInSctnStockQtyRepository repository;

	@Override
	protected CoreAbstIdentifRepo<StkLotInSctnStockQty> getRepo() {
		return repository;
	}

	public StkLotInSctnStockQty findBase(String artPic, String lotPic, String section) {
		List<StkLotInSctnStockQty> rl = repository
				.findByCntnrIdentifAndLotPicAndSectionAndCnsldtd(artPic, lotPic,section, Boolean.TRUE).firstResult(0).maxResults(1).getResultList();
		if (!rl.isEmpty()) return rl.iterator().next();
		return null;
	}

	public Long countByArtPicAndLotPicAndSectionAndSeqNbr(String artPic, String lotPic, String section, Integer seqNbr){
		return repository.findByCntnrIdentifAndLotPicAndSectionAndSeqNbr(artPic, lotPic,section, seqNbr).count();
	}
	public List<StkLotInSctnStockQty> findByArtPicAndLotPicAndSectionAndSeqNbr(
			String artPic, String lotPic, String section, Integer seqNbr, int start, int max) {
		return repository.findByCntnrIdentifAndLotPicAndSectionAndSeqNbr(artPic, lotPic,section, seqNbr).orderDesc("seqNbr").firstResult(start).maxResults(max).getResultList();
	}

	public StkLotInSctnStockQty findLatest(String artPic, String lotPic, String section) {
		List<StkLotInSctnStockQty> resultList = repository.findByCntnrIdentifAndLotPicAndSection(artPic,lotPic,section).orderDesc("seqNbr").firstResult(0).maxResults(1).getResultList();
		if(resultList.isEmpty()) return null;
		return resultList.iterator().next();
	}
	
	@Override
	protected Class<StkLotInSctnStockQty> getEntityClass() {
		return StkLotInSctnStockQty.class;
	}
}
