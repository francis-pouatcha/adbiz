package org.adorsys.adbnsptnr.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbnsptnr.jpa.BpPtnrCtgryDtls;
import org.adorsys.adbnsptnr.rest.BpPtnrCtgryDtlsEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class BpPtnrCtgryDtlsLoader extends
		AbstractObjectLoader<BpPtnrCtgryDtls> {

	@Inject
	private BpPtnrCtgryDtlsEJB ejb;

	@Override
	protected BpPtnrCtgryDtls newObject() {
		return new BpPtnrCtgryDtls();
	}

	public BpPtnrCtgryDtls findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	public BpPtnrCtgryDtls create(BpPtnrCtgryDtls entity) {
		return ejb.create(entity);
	}

	public BpPtnrCtgryDtls update(BpPtnrCtgryDtls found) {
		return ejb.update(found);
	}

	public BpPtnrCtgryDtls deleteById(String id) {
		return ejb.deleteById(id);
	}

}
