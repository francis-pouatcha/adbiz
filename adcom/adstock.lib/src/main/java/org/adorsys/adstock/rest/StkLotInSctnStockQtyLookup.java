package org.adorsys.adstock.rest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.repo.CoreAbstIdentifRepo;
import org.adorsys.adcore.rest.CoreAbstIdentifLookup;
import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.adstock.jpa.StkAbstStockQty;
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

//	public StkLotInSctnStockQty findBase(String artPic, String lotPic, String section) {
//		List<StkLotInSctnStockQty> rl = repository
//				.findByCntnrIdentifAndLotPicAndSectionAndCnsldtd(artPic, lotPic,section, Boolean.TRUE).firstResult(0).maxResults(1).getResultList();
//		if (!rl.isEmpty()) return rl.iterator().next();
//		return null;
//	}
//	public StkLotInSctnStockQty findBaseBefore(String artPic, String lotPic, String section, Date qtyDt) {
//		List<StkLotInSctnStockQty> rl = repository
//				.findByCntnrIdentifAndLotPicAndSectionAndCnsldtdAndQtyDtLessThan(artPic, lotPic,section, Boolean.TRUE, qtyDt).firstResult(0).maxResults(1).getResultList();
//		if (!rl.isEmpty()) return rl.iterator().next();
//		return null;
//	}

	public Long countByArtPicAndLotPicAndSectionAndSeqNbr(String artPic, String lotPic, String section, Integer seqNbr){
		return repository.findByCntnrIdentifAndLotPicAndSectionAndSeqNbr(artPic, lotPic,section, seqNbr).count();
	}
	public List<StkLotInSctnStockQty> findByArtPicAndLotPicAndSectionAndSeqNbr(
			String artPic, String lotPic, String section, Integer seqNbr, int start, int max) {
		return repository.findByCntnrIdentifAndLotPicAndSectionAndSeqNbr(artPic, lotPic,section, seqNbr).orderDesc("seqNbr").firstResult(start).maxResults(max).getResultList();
	}
	public List<StkLotInSctnStockQty> findByArtPicAndLotPicAndSectionAndSeqNbr(
			String artPic, String lotPic, String section, Integer seqNbr) {
		return repository.findByCntnrIdentifAndLotPicAndSectionAndSeqNbr(artPic, lotPic,section, seqNbr).orderDesc("seqNbr").getResultList();
	}

	public StkLotInSctnStockQty findLatest(String artPic, String lotPic, String section) {
		List<StkLotInSctnStockQty> resultList = repository.findByCntnrIdentifAndLotPicAndSection(artPic,lotPic,section).orderDesc("seqNbr").firstResult(0).maxResults(1).getResultList();
		if(resultList.isEmpty()) return null;
		return resultList.iterator().next();
	}
	public StkLotInSctnStockQty findLatestBefore(String artPic, String lotPic, String section, Date qtyDt) {
		List<StkLotInSctnStockQty> resultList = repository.findByCntnrIdentifAndLotPicAndSectionAndQtyDtLessThan(artPic,lotPic,section,qtyDt).orderDesc("seqNbr").firstResult(0).maxResults(1).getResultList();
		if(resultList.isEmpty()) return null;
		return resultList.iterator().next();
	}

	public StkLotInSctnStockQty findStockQtyBeforeDate(String artPic, String lotPic, String section, Date qtyDt) {
		StkLotInSctnStockQty findLatestBefore = findLatestBefore(artPic, lotPic, section, qtyDt);
		if(findLatestBefore==null) return null;
		Long count = countByArtPicAndLotPicAndSectionAndSeqNbr(artPic, lotPic, section, findLatestBefore.getSeqNbr());
		if(count==1)return findLatestBefore;
		List<StkLotInSctnStockQty> latestQties = findByArtPicAndLotPicAndSectionAndSeqNbr(artPic, lotPic, section, findLatestBefore.getSeqNbr());
		StkLotInSctnStockQty stockQty = new StkLotInSctnStockQty();
		findLatestBefore.copyTo(stockQty);
		readStock(stockQty, latestQties);
		return stockQty;
	}
	private void readStock(final StkLotInSctnStockQty stockQty, List<? extends StkAbstStockQty> latestQties){
		
		StkAbstStockQty latestQty = null;
		if(!latestQties.isEmpty()){
			latestQty = latestQties.iterator().next();
			stockQty.setParentRcrd(latestQty.getId());
		}

		BigDecimal reservedBeforeMe = latestQty==null?BigDecimal.ZERO:latestQty.getPrcdngRsvdQty();
		BigDecimal stockBeforeMe = latestQty==null?BigDecimal.ZERO:latestQty.getPrcdngStkQty();
		
		for (StkAbstStockQty stkAbstStockQty : latestQties) {
			reservedBeforeMe = BigDecimalUtils.sum(reservedBeforeMe,stkAbstStockQty.getRsvrdQty());
			stockBeforeMe = BigDecimalUtils.sum(stockBeforeMe,stkAbstStockQty.getStockQty());
		}		
		
		stockQty.setPrcdngStkQty(stockBeforeMe);
		stockQty.setPrcdngRsvdQty(reservedBeforeMe);
		stockQty.setSeqNbr(latestQty==null?0:latestQty.getSeqNbr()+1);
	}
	
	@Override
	protected Class<StkLotInSctnStockQty> getEntityClass() {
		return StkLotInSctnStockQty.class;
	}
}
