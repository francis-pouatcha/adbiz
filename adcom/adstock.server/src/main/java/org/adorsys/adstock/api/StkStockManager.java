package org.adorsys.adstock.api;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.auth.AdcomUser;
import org.adorsys.adcore.auth.AdcomUserFactory;
import org.adorsys.adcore.exceptions.AdException;
import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.jpa.StkLotInSctnStockQty;
import org.adorsys.adstock.jpa.StkMvnt;
import org.adorsys.adstock.rest.StkArticleLotLookup;
import org.adorsys.adstock.rest.StkLotInSctnStockQtyLookup;
import org.adorsys.adstock.rest.StkMvntEJB;
import org.adorsys.adstock.vo.StkMvntDto;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class StkStockManager  {

	@Inject
	private StkLotInSctnStockQtyLookup lotInSctnStockQtyLookup;
	
	@Inject
	private StkMvntEJB stkMvntEJB;

	@Resource
	private SessionContext context;

	@Inject
	private StkArticleLotLookup articleLotLookup;
	/**
	 * Moving in a quantity of a certain article to a certain position in the 
	 * warehouse. We have to know:
	 * 
	 * - The lotPic. We will use this to reload the article lot from the database.
	 * - StkMvnt additional informations. Document numbers.
	 * - Mvnt source and Mvnt destination.
	 * 
	 * @param stkMvnt
	 * @return
	 * @throws AdException 
	 */
	public StkMvnt moveIn(StkMvntDto stkMvnt) throws AdException {
		StkMvnt perstMvnt = initStkMvnt(stkMvnt);
		// Information we are interested in from outside.
		BigDecimal trgtQty = BigDecimalUtils.zeroIfNull(stkMvnt.getTrgtQty());
		if(!BigDecimalUtils.greaterZero(trgtQty)){
			trgtQty = BigDecimalUtils.negate(trgtQty);
		}
		perstMvnt.setTrgtQty(trgtQty);
		perstMvnt.setMvntDestIdentif(stkMvnt.getSection());
		perstMvnt.setMvntDest(stkMvnt.getMvntTerminal());
		stkMvntEJB.create(perstMvnt);
		return perstMvnt;
	}
	
	
	private StkMvnt initStkMvnt(StkMvntDto stkMvnt) throws AdException {
		// The lotPic hast to be a vaild one.
		String lotPic = stkMvnt.getLotPic();
		if(StringUtils.isBlank(lotPic))throw new AdException("Missing lot pic");
		Long countByLotPic = articleLotLookup.countByLotPic(lotPic);
		if(countByLotPic<=0) throw new AdException("No Article with lot pic : " + lotPic);
		StkArticleLot articleLot = articleLotLookup.findByLotPic(lotPic);
		StkMvnt perstMvnt = new StkMvnt();
		
		// Basic Data
		perstMvnt.copyFrom(articleLot); // First collect from the article lot.		
		perstMvnt.copyFrom(stkMvnt); // Then override with stock movement.
		perstMvnt.setIdentif(null);
		
		BigDecimal expectedStockQty = readStockQty(perstMvnt.getArtPic(), perstMvnt.getLotPic(), perstMvnt.getSection());
		
		Date now = new Date();
		perstMvnt.setValueDt(now);
		String currentLoginName = AdcomUserFactory.getAdcomUser(context).getLoginName();
		perstMvnt.setAcsngUser(currentLoginName);
		perstMvnt.setAcsngDt(now);
		perstMvnt.setQtyBefore(expectedStockQty);
		
		return perstMvnt;
	}

	public StkMvnt moveOut(StkMvntDto stkMvnt) throws AdException {
		StkMvnt perstMvnt = initStkMvnt(stkMvnt);
		// Information we are interested in from outside.
		BigDecimal trgtQty = BigDecimalUtils.zeroIfNull(stkMvnt.getTrgtQty());
		if(BigDecimalUtils.greaterZero(trgtQty)){
			trgtQty = BigDecimalUtils.negate(trgtQty);
		}
		perstMvnt.setTrgtQty(trgtQty);
		perstMvnt.setMvntOrigin(stkMvnt.getMvntTerminal());
		perstMvnt.setMvntOriginIdentif(stkMvnt.getSection());
		stkMvntEJB.create(perstMvnt);
		return perstMvnt;
	}

	private BigDecimal readStockQty(String artPic, String lotPic, String section){
		StkLotInSctnStockQty sctnStockQty = lotInSctnStockQtyLookup.findLatest(artPic, lotPic, section);
		// Read the current stock quantity. This is very important.
		// An inventory should always be adaptive. If the expected quatity 
		// of an inventory item changes, we can not modify that item anymore.
		// This method will create a new item.
		return sctnStockQty!=null?sctnStockQty.getStockQty():BigDecimal.ZERO;
	}


	public void transfer(StkMvntDto mvnOut, StkMvntDto mvnIn) throws AdException {
		moveOut(mvnOut);
		moveIn(mvnIn);
		
	}
}
