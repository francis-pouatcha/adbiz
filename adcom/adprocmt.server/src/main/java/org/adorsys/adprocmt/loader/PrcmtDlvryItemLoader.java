package org.adorsys.adprocmt.loader;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchInput;
import org.adorsys.adcore.loader.ejb.CorLdrStepCallback;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectLookup;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectManager;
import org.adorsys.adcore.xls.CoreAbstBsnsitemLoader;
import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.StepCallback;
import org.adorsys.adprocmt.api.PrcmtDeliveryManager;
import org.adorsys.adprocmt.jpa.PrcmtDelivery;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryCstr;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryHstry;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;
import org.adorsys.adprocmt.jpa.PrcmtJob;
import org.adorsys.adprocmt.jpa.PrcmtStep;
import org.adorsys.adprocmt.rest.PrcmtDeliveryLookup;

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
	
	@Override
	protected PrcmtDlvryItem newObject() {
		return new PrcmtDlvryItem();
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
}
