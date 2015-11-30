package org.adorsys.adprocmt.loader;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchInput;
import org.adorsys.adcore.loader.ejb.CorLdrStepCallback;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectManager;
import org.adorsys.adcore.xls.CoreAbstBsnsObjectLoader;
import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.StepCallback;
import org.adorsys.adprocmt.api.PrcmtDeliveryManager;
import org.adorsys.adprocmt.jpa.PrcmtDelivery;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryCstr;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryHstry;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;
import org.adorsys.adprocmt.jpa.PrcmtJob;
import org.adorsys.adprocmt.jpa.PrcmtStep;

@Stateless
public class PrcmtDeliveryLoader extends CoreAbstBsnsObjectLoader<PrcmtDelivery, PrcmtDlvryItem, PrcmtDeliveryHstry, PrcmtJob, PrcmtStep, PrcmtDeliveryCstr, CoreAbstBsnsObjectSearchInput<PrcmtDelivery>> {

	@EJB
	private PrcmtDeliveryManager manager;
	@EJB
	private PrcmtDeliveryLoader loader;
	@EJB
	private CorLdrStepCallback stepCallback;
	
	@Override
	protected CoreAbstBsnsObjectManager<PrcmtDelivery, PrcmtDlvryItem, PrcmtDeliveryHstry, PrcmtJob, PrcmtStep, PrcmtDeliveryCstr, CoreAbstBsnsObjectSearchInput<PrcmtDelivery>> getManager() {
		return manager;
	}

	@Override
	protected PrcmtDelivery newObject() {
		return new PrcmtDelivery();
	}

	@Override
	protected CoreAbstLoader<PrcmtDelivery> getLoader() {
		return loader;
	}

	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}
}
