package org.adorsys.adbnsptnr.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbnsptnr.jpa.BpInsurrance;
import org.adorsys.adbnsptnr.rest.BpInsurranceEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class BpInsurranceLoader extends AbstractObjectLoader<BpInsurrance> {

	@Inject
	private BpInsurranceEJB ejb;

	@Override
	protected BpInsurrance newObject() {
		return new BpInsurrance();
	}

	public BpInsurrance findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	public BpInsurrance create(BpInsurrance entity) {
		return ejb.create(entity);
	}

	public BpInsurrance update(BpInsurrance found) {
		return ejb.update(found);
	}

	public BpInsurrance deleteById(String id) {
		return ejb.deleteById(id);
	}

}
