package org.adorsys.adinvtry.loader;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.jpa.CoreAbstBsnsObjectSearchInput;
import org.adorsys.adcore.loader.ejb.CorLdrStepCallback;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectLookup;
import org.adorsys.adcore.rest.CoreAbstBsnsObjectManager;
import org.adorsys.adcore.utils.SequenceGenerator;
import org.adorsys.adcore.xls.CoreAbstBsnsitemLoader;
import org.adorsys.adcore.xls.CoreAbstLoader;
import org.adorsys.adcore.xls.PropertyDesc;
import org.adorsys.adcore.xls.StepCallback;
import org.adorsys.adinvtry.api.InvInvtryManager;
import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtryCstr;
import org.adorsys.adinvtry.jpa.InvInvtryHstry;
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.jpa.InvJob;
import org.adorsys.adinvtry.jpa.InvStep;
import org.adorsys.adinvtry.rest.InvInvtryLookup;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class InvInvtryItemLoader extends CoreAbstBsnsitemLoader<InvInvtry, InvInvtryItem, InvInvtryHstry, InvJob, InvStep, InvInvtryCstr, CoreAbstBsnsObjectSearchInput<InvInvtry>>{

	@Inject
	private InvInvtryManager manager;
	@Inject
	private InvInvtryLookup lookup;
	@EJB
	private InvInvtryItemLoader loader;
	@EJB
	private CorLdrStepCallback stepCallback;

	@Override
	protected InvInvtryItem newObject() {
		return new InvInvtryItem();
	}

	@Override
	protected CoreAbstBsnsObjectManager<InvInvtry, InvInvtryItem, InvInvtryHstry, InvJob, InvStep, InvInvtryCstr, CoreAbstBsnsObjectSearchInput<InvInvtry>> getManager() {
		return manager;
	}

	@Override
	protected CoreAbstBsnsObjectLookup<InvInvtry> getLookup() {
		return lookup;
	}

	@Override
	public InvInvtryItem save(InvInvtryItem invtryItem, List<PropertyDesc> fields) {
		if(StringUtils.isBlank(invtryItem.getLotPic())){
			String lotPic = SequenceGenerator.getSequence(SequenceGenerator.LOT_SEQUENCE_PREFIXE);
			invtryItem.setLotPic(lotPic);
		}
		return super.save(invtryItem, fields);
	}

	@Override
	protected CoreAbstLoader<InvInvtryItem> getLoader() {
		return loader;
	}

	@Override
	protected StepCallback getStepCallback() {
		return stepCallback;
	}
}
