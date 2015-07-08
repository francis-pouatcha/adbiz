package org.adorsys.adbase.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.OuWorkspace;
import org.adorsys.adbase.rest.OuWorkspaceEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class OuWorkspaceLoader extends AbstractObjectLoader<OuWorkspace> {

	@Inject
	private OuWorkspaceEJB ejb;

	@Override
	protected OuWorkspace newObject() {
		return new OuWorkspace();
	}

	public OuWorkspace findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	public OuWorkspace create(OuWorkspace entity) {
		return ejb.create(entity);
	}

	public OuWorkspace update(OuWorkspace found) {
		return ejb.update(found);
	}

	public OuWorkspace deleteById(String id) {
		return ejb.deleteById(id);
	}

}
