			package org.adorsys.adprocmt.loader;


import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.exceptions.AdRestException;
import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchInput;
import org.adorsys.adcore.loader.ejb.CorLdrStepCallback;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectLookup;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectManager;
import org.adorsys.adcore.xls.CoreAbstBsnsitemLoader;
import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.PropertyDesc;
import org.adorsys.adcore.xls.StepCallback;
import org.adorsys.adprocmt.api.PrcmtDeliveryManager;
import org.adorsys.adprocmt.api.PrcmtDlvryItemExcel;
import org.adorsys.adprocmt.jpa.PrcmtDelivery;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryCstr;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryHstry;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;
import org.adorsys.adprocmt.jpa.PrcmtJob;
import org.adorsys.adprocmt.jpa.PrcmtStep;
import org.adorsys.adprocmt.rest.PrcmtDeliveryLookup;
import org.adorsys.adprocmt.rest.PrcmtDlvry2OuLookup;
import org.adorsys.adprocmt.rest.PrcmtDlvryItem2StrgSctnLookup;
import org.adorsys.adstock.rest.StkArtStockQtyLookup;
import org.adorsys.adstock.rest.StkLotInSctnStockQtyLookup;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class PrcmtDlvryItemLoader extends CoreAbstBsnsitemLoader<PrcmtDelivery, PrcmtDlvryItem, PrcmtDeliveryHstry, PrcmtJob, PrcmtStep, PrcmtDeliveryCstr, CoreAbstBsnsObjectSearchInput<PrcmtDelivery>> {

	@Inject
	private PrcmtDeliveryManager manager;
	@Inject
	private PrcmtDeliveryLookup lookup;
	@Inject
	private PrcmtDlvryItemLoader loader;
	@EJB
	private CorLdrStepCallback stepCallback;
	
	@EJB
	private PrcmtDlvry2OuLookup dlvry2OuLookup;
	@EJB
	private PrcmtDlvryItem2StrgSctnLookup dlvryItem2StrgSctnLookup;
	@EJB
	private StkLotInSctnStockQtyLookup lotInSctnStockQtyLookup;
	@EJB
	private StkArtStockQtyLookup artStockQtyLookup;

	@Override
	protected PrcmtDlvryItem newObject() {
		return new PrcmtDlvryItemExcel();
	}

	@Override
	protected CoreAbstBsnsObjectManager<PrcmtDelivery, PrcmtDlvryItem, PrcmtDeliveryHstry, PrcmtJob, PrcmtStep, PrcmtDeliveryCstr, CoreAbstBsnsObjectSearchInput<PrcmtDelivery>> getManager() {
		return manager;
	}

	@Override
	protected CoreAbstBsnsObjectLookup<PrcmtDelivery> getLookup() {
		return lookup;
	}

	@Override
	protected CoreAbstLoader<PrcmtDlvryItem> getLoader() {
		return loader;
	}

	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}

	@Override
	public PrcmtDlvryItem save(PrcmtDlvryItem entity, List<PropertyDesc> fields) {
		
		String recvngOus = null;
		String strgSctns = null;
		Date now = new Date();
		if(entity instanceof PrcmtDlvryItemExcel){
			PrcmtDlvryItemExcel itemExcel = (PrcmtDlvryItemExcel) entity;
			recvngOus = itemExcel.getRecvngOus();
			strgSctns = itemExcel.getStrgSctns();
			PrcmtDlvryItem prcmtDlvryItem = new PrcmtDlvryItem();
			prcmtDlvryItem.copyFrom(entity);
			entity = prcmtDlvryItem;
			entity.setValueDt(now);
		}		
		
		if(StringUtils.isBlank(entity.getCntnrIdentif())) throw new IllegalStateException("Missing cntnrIdentif for PrcmtDlvryItem with artPic: " + entity.getArtPic());
		
		try {
			return manager.addItem(entity.getCntnrIdentif(), entity, strgSctns, recvngOus);
		} catch (AdRestException e) {
			throw new IllegalStateException(e);
		}

	}

	@Override
	public boolean done(PrcmtDlvryItem last) {
		String cntnrIdentif = last.getCntnrIdentif();
		try {
			manager.close(cntnrIdentif);
			manager.post(cntnrIdentif);
		} catch (AdRestException es){
			throw new IllegalStateException(es);
		}
		return true;
	}
}
