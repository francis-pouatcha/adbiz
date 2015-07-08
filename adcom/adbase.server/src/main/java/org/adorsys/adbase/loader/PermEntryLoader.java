package org.adorsys.adbase.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.PermEntry;
import org.adorsys.adbase.rest.PermEntryEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class PermEntryLoader extends AbstractObjectLoader<PermEntry> {

	@Inject
	private PermEntryEJB ejb;

	@Override
	protected PermEntry newObject() {
		return new PermEntry();
	}

	public PermEntry findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	public PermEntry create(PermEntry entity) {
		return ejb.create(entity);
	}

	public PermEntry update(PermEntry found) {
		return ejb.update(found);
	}

	public PermEntry deleteById(String id) {
		return ejb.deleteById(id);
	}

}
