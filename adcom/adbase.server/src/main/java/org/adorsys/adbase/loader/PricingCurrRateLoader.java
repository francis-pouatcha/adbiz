package org.adorsys.adbase.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.PricingCurrRate;
import org.adorsys.adbase.rest.PricingCurrRateEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class PricingCurrRateLoader extends
		AbstractObjectLoader<PricingCurrRate> {

	@Inject
	private PricingCurrRateEJB ejb;

	@Override
	protected PricingCurrRate newObject() {
		return new PricingCurrRate();
	}

	public PricingCurrRate findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	public PricingCurrRate create(PricingCurrRate entity) {
		return ejb.create(entity);
	}

	public PricingCurrRate update(PricingCurrRate found) {
		return ejb.update(found);
	}

	public PricingCurrRate deleteById(String id) {
		return ejb.deleteById(id);
	}

}
