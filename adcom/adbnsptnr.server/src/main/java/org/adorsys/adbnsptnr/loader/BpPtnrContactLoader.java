package org.adorsys.adbnsptnr.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbnsptnr.jpa.BpPtnrContact;
import org.adorsys.adbnsptnr.rest.BpPtnrContactEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class BpPtnrContactLoader extends AbstractObjectLoader<BpPtnrContact> {

	@Inject
	private BpPtnrContactEJB ejb;

	@Override
	protected BpPtnrContact newObject() {
		return new BpPtnrContact();
	}

	public BpPtnrContact findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	public BpPtnrContact create(BpPtnrContact entity) {
		return ejb.create(entity);
	}

	public BpPtnrContact update(BpPtnrContact found) {
		return ejb.update(found);
	}

	public BpPtnrContact deleteById(String id) {
		return ejb.deleteById(id);
	}

}
