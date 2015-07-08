package org.adorsys.adbnsptnr.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbnsptnr.jpa.BpBnsPtnr;
import org.adorsys.adbnsptnr.rest.BpBnsPtnrEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class BpBnsPtnrLoader extends AbstractObjectLoader<BpBnsPtnr> {

	@Inject
	private BpBnsPtnrEJB ejb;

	@Override
	protected BpBnsPtnr newObject() {
		return new BpBnsPtnr();
	}

	public BpBnsPtnr findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif);
	}

	public BpBnsPtnr create(BpBnsPtnr entity) {
		return ejb.create(entity);
	}

	public BpBnsPtnr update(BpBnsPtnr found) {
		return ejb.update(found);
	}

	public BpBnsPtnr deleteById(String id) {
		return ejb.deleteById(id);
	}

}
