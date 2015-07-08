package org.adorsys.adbase.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.OrgUnit;
import org.adorsys.adbase.rest.OrgUnitEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class OrgUnitLoader extends AbstractObjectLoader<OrgUnit> {

	@Inject
	private OrgUnitEJB ejb;

	@Override
	protected OrgUnit newObject() {
		return new OrgUnit();
	}

	public OrgUnit findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	public OrgUnit create(OrgUnit entity) {
		return ejb.create(entity);
	}

	public OrgUnit update(OrgUnit found) {
		return ejb.update(found);
	}

	public OrgUnit deleteById(String id) {
		return ejb.deleteById(id);
	}

}
