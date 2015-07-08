package org.adorsys.adbase.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.Workspace;
import org.adorsys.adbase.rest.WorkspaceEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class WorkspaceLoader extends AbstractObjectLoader<Workspace> {

	@Inject
	private WorkspaceEJB ejb;

	@Override
	protected Workspace newObject() {
		return new Workspace();
	}

	public Workspace findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	public Workspace create(Workspace entity) {
		return ejb.create(entity);
	}

	public Workspace update(Workspace found) {
		return ejb.update(found);
	}

	public Workspace deleteById(String id) {
		return ejb.deleteById(id);
	}

}
