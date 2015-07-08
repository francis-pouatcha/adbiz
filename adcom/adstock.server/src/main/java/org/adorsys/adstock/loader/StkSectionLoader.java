package org.adorsys.adstock.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.xls.AbstractObjectLoader;
import org.adorsys.adstock.jpa.StkSection;
import org.adorsys.adstock.rest.StkSectionEJB;

@Stateless
public class StkSectionLoader extends AbstractObjectLoader<StkSection> {

	@Inject
	private StkSectionEJB ejb;

	@Override
	protected StkSection newObject() {
		return new StkSection();
	}

	public StkSection findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	public StkSection create(StkSection entity) {
		return ejb.create(entity);
	}

	public StkSection update(StkSection found) {
		return ejb.update(found);
	}

	public StkSection deleteById(String id) {
		return ejb.deleteById(id);
	}

}
