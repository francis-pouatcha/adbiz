package org.adorsys.adbnsptnr.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbnsptnr.jpa.BpPtnrCtgry;
import org.adorsys.adbnsptnr.rest.BpPtnrCtgryEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class BpPtnrCtgryLoader extends AbstractObjectLoader<BpPtnrCtgry> {

	@Inject
	private BpPtnrCtgryEJB ejb;

	@Override
	protected BpPtnrCtgry newObject() {
		return new BpPtnrCtgry();
	}

	public BpPtnrCtgry findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif);
	}

	public BpPtnrCtgry create(BpPtnrCtgry entity) {
		return ejb.create(entity);
	}

	public BpPtnrCtgry update(BpPtnrCtgry found) {
		return ejb.update(found);
	}

	public BpPtnrCtgry deleteById(String id) {
		return ejb.deleteByIdentif(id);
	}

}
