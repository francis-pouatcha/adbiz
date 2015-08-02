package org.adorsys.adinvtry.loader;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchInput;
import org.adorsys.adcore.loader.ejb.CorLdrStepCallback;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectManager;
import org.adorsys.adcore.xls.CoreAbstBsnsObjectLoader;
import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.StepCallback;
import org.adorsys.adinvtry.api.InvInvtryManager;
import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtryCstr;
import org.adorsys.adinvtry.jpa.InvInvtryHstry;
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.jpa.InvJob;
import org.adorsys.adinvtry.jpa.InvStep;

@Stateless
public class InvInvtryLoader extends CoreAbstBsnsObjectLoader<InvInvtry, InvInvtryItem, InvInvtryHstry, InvJob, InvStep, InvInvtryCstr, CoreAbstBsnsObjectSearchInput<InvInvtry>>{
	@EJB
	private InvInvtryManager manager;
	@EJB
	private InvInvtryLoader loader;
	@EJB
	private CorLdrStepCallback stepCallback;

	@Override
	protected InvInvtry newObject() {
		return new InvInvtry();
	}

	@Override
	protected CoreAbstBsnsObjectManager<InvInvtry, InvInvtryItem, InvInvtryHstry, InvJob, InvStep, InvInvtryCstr, CoreAbstBsnsObjectSearchInput<InvInvtry>> getManager() {
		return manager;
	}

	@Override
	protected CoreAbstLoader<InvInvtry> getLoader() {
		return loader;
	}

	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}
}
