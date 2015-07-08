package org.adorsys.adbnsptnr.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbnsptnr.jpa.BpInsrrncPpse;
import org.adorsys.adbnsptnr.rest.BpInsrrncPpseEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class BpInsrrncPpseLoader extends AbstractObjectLoader<BpInsrrncPpse> {

	@Inject
	private BpInsrrncPpseEJB ejb;

	@Override
	protected BpInsrrncPpse newObject() {
		return new BpInsrrncPpse();
	}

	public BpInsrrncPpse findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	public BpInsrrncPpse create(BpInsrrncPpse entity) {
		return ejb.create(entity);
	}

	public BpInsrrncPpse update(BpInsrrncPpse found) {
		return ejb.update(found);
	}

	public BpInsrrncPpse deleteById(String id) {
		return ejb.deleteById(id);
	}

}
