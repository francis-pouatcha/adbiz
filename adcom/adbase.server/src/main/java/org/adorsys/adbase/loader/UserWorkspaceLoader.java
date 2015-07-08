package org.adorsys.adbase.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.UserWorkspace;
import org.adorsys.adbase.rest.UserWorkspaceEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class UserWorkspaceLoader extends AbstractObjectLoader<UserWorkspace> {

	@Inject
	private UserWorkspaceEJB ejb;

	@Override
	protected UserWorkspace newObject() {
		return new UserWorkspace();
	}

	public UserWorkspace findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	public UserWorkspace create(UserWorkspace entity) {
		return ejb.create(entity);
	}

	public UserWorkspace update(UserWorkspace found) {
		return ejb.update(found);
	}

	public UserWorkspace deleteById(String id) {
		return ejb.deleteById(id);
	}

}
