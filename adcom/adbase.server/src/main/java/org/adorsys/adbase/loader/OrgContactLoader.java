package org.adorsys.adbase.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.OrgContact;
import org.adorsys.adbase.rest.OrgContactEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class OrgContactLoader extends AbstractObjectLoader<OrgContact> {

	@Inject
	private OrgContactEJB ejb;

	@Override
	protected OrgContact newObject() {
		return new OrgContact();
	}

	public OrgContact findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	public OrgContact create(OrgContact entity) {
		return ejb.create(entity);
	}

	public OrgContact update(OrgContact found) {
		return ejb.update(found);
	}

	public OrgContact deleteById(String id) {
		return ejb.deleteById(id);
	}

}
