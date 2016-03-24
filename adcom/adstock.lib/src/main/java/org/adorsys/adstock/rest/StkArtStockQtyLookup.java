package org.adorsys.adstock.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adstock.jpa.StkArtStockQty;
import org.adorsys.adstock.repo.StkArtStockQtyRepository;

@Stateless
public class StkArtStockQtyLookup extends
		CoreAbstIdentifLookup<StkArtStockQty> {

	@Inject
	private StkArtStockQtyRepository repository;

	@Override
	protected CoreAbstIdentifRepo<StkArtStockQty> getRepo() {
		return repository;
	}

//	public StkArtStockQty findBase(String artPic) {
//		List<StkArtStockQty> rl = repository.findByCntnrIdentifAndCnsldtd(artPic, Boolean.TRUE).orderDesc("seqNbr").firstResult(0).maxResults(1).getResultList();
//		if (!rl.isEmpty()) return rl.iterator().next();
//		return null;
//	}

	public List<StkArtStockQty> findByArtPicOrderBySeqNbrDesc(String artPic, int start, int max) {
		return repository.findByCntnrIdentif(artPic).orderDesc("seqNbr").firstResult(start).maxResults(max).getResultList();
	}

	public Long countByArtPicAndSeqNbr(String artPic, Integer seqNbr) {
		return repository.findByCntnrIdentifAndSeqNbr(artPic, seqNbr).count();
	}
	public List<StkArtStockQty> findByArtPicAndSeqNbr(
			String artPic, Integer seqNbr, int start, int max) {
		return repository.findByCntnrIdentifAndSeqNbr(artPic, seqNbr).orderDesc("seqNbr").firstResult(start).maxResults(max).getResultList();
	}
	public List<StkArtStockQty> findByArtPicAndSeqNbr(String artPic, Integer seqNbr) {
		return repository.findByCntnrIdentifAndSeqNbr(artPic, seqNbr).orderDesc("seqNbr").getResultList();
	}
	
	public StkArtStockQty findLatest(String artPic) {
		List<StkArtStockQty> resultList = repository.findByCntnrIdentif(artPic).orderDesc("seqNbr").firstResult(0).maxResults(1).getResultList();
		if(resultList.isEmpty()) return null;
		return resultList.iterator().next();
	}
	
	@Override
	protected Class<StkArtStockQty> getEntityClass() {
		return StkArtStockQty.class;
	}
}
