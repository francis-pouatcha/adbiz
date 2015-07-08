package org.adorsys.adbnsptnr.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbnsptnr.jpa.BpPtnrCreditDtls;
import org.adorsys.adbnsptnr.rest.BpPtnrCreditDtlsEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class BpPtnrCreditDtlsLoader extends
		AbstractObjectLoader<BpPtnrCreditDtls> {

	@Inject
	private BpPtnrCreditDtlsEJB ejb;

	@Override
	protected BpPtnrCreditDtls newObject() {
		return new BpPtnrCreditDtls();
	}

	public BpPtnrCreditDtls findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	public BpPtnrCreditDtls create(BpPtnrCreditDtls entity) {
		return ejb.create(entity);
	}

	public BpPtnrCreditDtls update(BpPtnrCreditDtls found) {
		return ejb.update(found);
	}

	public BpPtnrCreditDtls deleteById(String id) {
		return ejb.deleteById(id);
	}

}
