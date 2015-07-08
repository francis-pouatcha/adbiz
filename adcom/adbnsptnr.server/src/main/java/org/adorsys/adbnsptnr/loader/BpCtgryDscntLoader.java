package org.adorsys.adbnsptnr.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbnsptnr.jpa.BpPtnrContract;
import org.adorsys.adbnsptnr.rest.BpPtnrContractEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class BpCtgryDscntLoader extends AbstractObjectLoader<BpPtnrContract> {

	@Inject
	private BpPtnrContractEJB ejb;

	@Override
	protected BpPtnrContract newObject() {
		return new BpPtnrContract();
	}

	public BpPtnrContract findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	public BpPtnrContract create(BpPtnrContract entity) {
		return ejb.create(entity);
	}

	public BpPtnrContract update(BpPtnrContract found) {
		return ejb.update(found);
	}

	public BpPtnrContract deleteById(String id) {
		return ejb.deleteById(id);
	}

}
