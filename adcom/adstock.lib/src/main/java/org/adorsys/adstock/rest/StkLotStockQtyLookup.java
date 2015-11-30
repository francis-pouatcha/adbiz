package org.adorsys.adstock.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adstock.jpa.StkLotStockQty;
import org.adorsys.adstock.repo.StkLotStockQtyRepository;

@Stateless
public class StkLotStockQtyLookup extends
		CoreAbstIdentifLookup<StkLotStockQty> {

	@Inject
	private StkLotStockQtyRepository repository;

	@Override
	protected CoreAbstIdentifRepo<StkLotStockQty> getRepo() {
		return repository;
	}

	public StkLotStockQty findBase(String artPic, String lotPic) {
		List<StkLotStockQty> rl = repository.findByCntnrIdentifAndLotPicAndCnsldtd(artPic, lotPic, Boolean.TRUE).firstResult(0).maxResults(1).getResultList();
		if (!rl.isEmpty()) return rl.iterator().next();
		return null;
	}

	public Long countByArtPicAndLotPicAndSeqNbr(String artPic, String lotPic, Integer seqNbr) {
		return repository.findByCntnrIdentifAndLotPicAndSeqNbr(artPic, lotPic,seqNbr).count();
	}
	public List<StkLotStockQty> findByArtPicAndLotPicAndSeqNbr(
			String artPic, String lotPic, Integer seqNbr, int start, int max) {
		return repository.findByCntnrIdentifAndLotPicAndSeqNbr(artPic, lotPic,seqNbr).orderDesc("seqNbr").firstResult(start).maxResults(max).getResultList();
	}

	public Long countByArtPicAndLotPic(String artPic, String lotPic) {
		return repository.findByCntnrIdentifAndLotPic(artPic, lotPic).count();
	}
	public List<StkLotStockQty> findByArtPicAndLotPicOrderBySeqNbrDesc(String artPic, String lotPic, int start, int max) {
		return repository.findByCntnrIdentifAndLotPic(artPic, lotPic).orderDesc("seqNbr").firstResult(start).maxResults(max).getResultList();
	}

	public StkLotStockQty findLatest(String artPic, String lotPic) {
		List<StkLotStockQty> resultList = repository.findByCntnrIdentifAndLotPic(artPic,lotPic).orderDesc("seqNbr").firstResult(0).maxResults(1).getResultList();
		if(resultList.isEmpty()) return null;
		return resultList.iterator().next();
	}
	
	@Override
	protected Class<StkLotStockQty> getEntityClass() {
		return StkLotStockQty.class;
	}
}
