package org.adorsys.adprocmt.trigger;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArticle;
import org.adorsys.adcatal.rest.CatalArticleLookup;
import org.adorsys.adcore.exceptions.AdRestException;
import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.adprocmt.api.PrcmtOrderManager;
import org.adorsys.adprocmt.jpa.PrcmtPOItem;
import org.adorsys.adstock.jpa.StkArtStockQty;
/**
 * 
 * @author guymoyo
 *
 */
public class SimpleTriggerModeHandler {

	@Inject
	private CatalArticleLookup articleLookup;
	@Inject
	private PrcmtOrderManager orderManager;
	
	public void executeTriggerMode(String orderNbr, List<StkArtStockQty> articleStocks) throws AdRestException {	
		if(orderNbr==null || articleStocks==null || articleStocks.isEmpty()) return;
		Date now = new Date();
		for(StkArtStockQty itemStock:articleStocks){
			CatalArticle article = articleLookup.findByIdentif(itemStock.getCntnrIdentif());
			PrcmtPOItem poItem = new PrcmtPOItem();
			poItem.setAcsngDt(now);
			poItem.setArtPic(itemStock.getCntnrIdentif());
			poItem.setCntnrIdentif(orderNbr);
			poItem.setValueDt(now);
			BigDecimal maxStockQty = article.getMaxStockQty();
			BigDecimal stockQty = itemStock.getStockQty();
			poItem.setTrgtQty(BigDecimalUtils.subs(maxStockQty, stockQty));

			orderManager.addItem(orderNbr, poItem);
		}	
	}

}
