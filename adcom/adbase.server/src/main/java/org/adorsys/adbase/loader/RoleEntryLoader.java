package org.adorsys.adbase.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.RoleEntry;
import org.adorsys.adbase.rest.RoleEntryEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class RoleEntryLoader extends AbstractObjectLoader<RoleEntry> {

	@Inject
	private RoleEntryEJB ejb;

	@Override
	protected RoleEntry newObject() {
		return new RoleEntry();
	}

	public RoleEntry findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	public RoleEntry create(RoleEntry entity) {
		return ejb.create(entity);
	}

	public RoleEntry update(RoleEntry found) {
		return ejb.update(found);
	}

	public RoleEntry deleteById(String id) {
		return ejb.deleteById(id);
	}

}
