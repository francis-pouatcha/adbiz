package org.adorsys.adbase.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.WorkspaceRestriction;
import org.adorsys.adbase.rest.WorkspaceRestrictionEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class WorkspaceRestrictionLoader extends
		AbstractObjectLoader<WorkspaceRestriction> {

	@Inject
	private WorkspaceRestrictionEJB ejb;

	@Override
	protected WorkspaceRestriction newObject() {
		return new WorkspaceRestriction();
	}

	public WorkspaceRestriction findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	public WorkspaceRestriction create(WorkspaceRestriction entity) {
		return ejb.create(entity);
	}

	public WorkspaceRestriction update(WorkspaceRestriction found) {
		return ejb.update(found);
	}

	public WorkspaceRestriction deleteById(String id) {
		return ejb.deleteById(id);
	}

}
