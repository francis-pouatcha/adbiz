package org.adorsys.adstock.rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArtLangMapping;
import org.adorsys.adcatal.rest.CatalArtLangMappingLookup;
import org.adorsys.adcore.jpa.CoreAbstBsnsObject;
import org.adorsys.adcore.jpa.CoreAbstEntityCstr;
import org.adorsys.adcore.repo.CoreAbstBsnsItemRepo;
import org.adorsys.adcore.rest.CoreAbstBsnsItemEJB;
import org.adorsys.adcore.rest.CoreAbstBsnsObjInjector;
import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.jpa.StkArticleLotHstry;
import org.adorsys.adstock.jpa.StkJob;
import org.adorsys.adstock.jpa.StkLotStockQty;
import org.adorsys.adstock.jpa.StkStep;
import org.adorsys.adstock.repo.StkArticleLotRepository;
import org.apache.deltaspike.core.util.StringUtils;

@Stateless
public class StkArticleLotEJB extends CoreAbstBsnsItemEJB<CoreAbstBsnsObject, StkArticleLot, StkArticleLotHstry, StkJob, StkStep, CoreAbstEntityCstr> {

	@Inject
	private StkArticleLotRepository repo;
	
	@EJB
	private StkArticleLotInjector injector;
	
	@Inject
	CatalArtLangMappingLookup catalArtLangMappingLookup;
	
	@Inject
	StkLotStockQtyLookup stkLotStockQtyLookup;
	
	
	@Override
	protected CoreAbstBsnsObjInjector<CoreAbstBsnsObject, StkArticleLot, StkArticleLotHstry, StkJob, StkStep, CoreAbstEntityCstr> getInjector() {
		return injector;
	}

	@Override
	protected CoreAbstBsnsItemRepo<StkArticleLot> getBsnsRepo() {
		return repo;
	}
	
	public List<StkArticleLot> findStkArticeLotByArtName(String artName){
		List<StkArticleLot> result = new ArrayList<StkArticleLot>();
		List<StkArticleLot> articleLots = repo.findStkArticeLotByArtName("%"+artName+"%").firstResult(0).maxResults(10).getResultList();
		for(StkArticleLot artLot:articleLots){
			if(StringUtils.isEmpty(artLot.getArtName())){
				Iterator<CatalArtLangMapping> catalLangIterator = catalArtLangMappingLookup.findByArtPic(artLot.getArtPic(), 0, 1).iterator();
				if(catalLangIterator.hasNext()){
					artLot.setArtName(catalLangIterator.next().getArtName());
				}
			}
			
			StkLotStockQty lotStockQty = stkLotStockQtyLookup.findLatest(artLot.getArtPic(), artLot.getLotPic());
			if(lotStockQty==null) continue;
			artLot.setTrgtQty(lotStockQty.getStockQty());
			result.add(artLot);
		}
			
		return result;
	}

	public List<StkArticleLot> findStkArticeLotByLotPic(String lotPic) {
		List<StkArticleLot> result = new ArrayList<StkArticleLot>();
		List<StkArticleLot> articleLots = repo.findByLotPicLike("%"+lotPic+"%").firstResult(0).maxResults(10).getResultList();
		for(StkArticleLot artLot:articleLots){
			if(StringUtils.isEmpty(artLot.getArtName())){
				Iterator<CatalArtLangMapping> catalLangIterator = catalArtLangMappingLookup.findByArtPic(artLot.getArtPic(), 0, 1).iterator();
				if(catalLangIterator.hasNext()){
					artLot.setArtName(catalLangIterator.next().getArtName());
				}
			}
			
			StkLotStockQty lotStockQty = stkLotStockQtyLookup.findLatest(artLot.getArtPic(), artLot.getLotPic());
			if(lotStockQty==null) continue;
			artLot.setTrgtQty(lotStockQty.getStockQty());
			result.add(artLot);
		}
			
		return result;
	}
}
