package org.adorsys.adbase.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.Locality;
import org.adorsys.adbase.rest.LocalityEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class LocalityLoader extends AbstractObjectLoader<Locality> {

	@Inject
	private LocalityEJB ejb;

	@Override
	protected Locality newObject() {
		return new Locality();
	}

	public Locality findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	public Locality create(Locality entity) {
		return ejb.create(entity);
	}

	public Locality update(Locality found) {
		return ejb.update(found);
	}

	public Locality deleteById(String id) {
		return ejb.deleteById(id);
	}

}
