package org.adorsys.adbnsptnr.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbnsptnr.jpa.BpCtgryOfPtnr;
import org.adorsys.adbnsptnr.rest.BpCtgryOfPtnrEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class BpCtgryOfPtnrLoader extends CoreAbstEntityLoader<BpCtgryOfPtnr> {

	@Inject
	private BpCtgryOfPtnrEJB ejb;

	@Override
	protected BpCtgryOfPtnr newObject() {
		return new BpCtgryOfPtnr();
	}

	public BpCtgryOfPtnr findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	public BpCtgryOfPtnr create(BpCtgryOfPtnr entity) {
		return ejb.create(entity);
	}

	public BpCtgryOfPtnr update(BpCtgryOfPtnr found) {
		return ejb.update(found);
	}

	public BpCtgryOfPtnr deleteById(String id) {
		return ejb.deleteById(id);
	}

}
