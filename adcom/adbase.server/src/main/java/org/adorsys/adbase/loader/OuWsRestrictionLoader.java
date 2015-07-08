package org.adorsys.adbase.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.OuWsRestriction;
import org.adorsys.adbase.rest.OuWsRestrictionEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class OuWsRestrictionLoader extends
		AbstractObjectLoader<OuWsRestriction> {

	@Inject
	private OuWsRestrictionEJB ejb;

	@Override
	protected OuWsRestriction newObject() {
		return new OuWsRestriction();
	}

	public OuWsRestriction findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	public OuWsRestriction create(OuWsRestriction entity) {
		return ejb.create(entity);
	}

	public OuWsRestriction update(OuWsRestriction found) {
		return ejb.update(found);
	}

	public OuWsRestriction deleteById(String id) {
		return ejb.deleteById(id);
	}

}
