package org.adorsys.adbase.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.UserWsRestriction;
import org.adorsys.adbase.rest.UserWsRestrictionEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class UserWsRestrictionLoader extends
		AbstractObjectLoader<UserWsRestriction> {

	@Inject
	private UserWsRestrictionEJB ejb;

	@Override
	protected UserWsRestriction newObject() {
		return new UserWsRestriction();
	}

	public UserWsRestriction findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	public UserWsRestriction create(UserWsRestriction entity) {
		return ejb.create(entity);
	}

	public UserWsRestriction update(UserWsRestriction found) {
		return ejb.update(found);
	}

	public UserWsRestriction deleteById(String id) {
		return ejb.deleteById(id);
	}

}
