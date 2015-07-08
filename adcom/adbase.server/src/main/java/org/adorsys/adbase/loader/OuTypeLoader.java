package org.adorsys.adbase.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.OuType;
import org.adorsys.adbase.rest.OuTypeEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class OuTypeLoader extends AbstractObjectLoader<OuType> {

	@Inject
	private OuTypeEJB ejb;

	@Override
	protected OuType newObject() {
		return new OuType();
	}

	public OuType findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	public OuType create(OuType entity) {
		return ejb.create(entity);
	}

	public OuType update(OuType found) {
		return ejb.update(found);
	}

	public OuType deleteById(String id) {
		return ejb.deleteById(id);
	}

}
