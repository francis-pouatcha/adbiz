package org.adorsys.adprocmt.loader;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.xls.AbstractEnumLoader;
import org.adorsys.adprocmt.jpa.ProcmtPOTriggerMode;
import org.adorsys.adprocmt.rest.ProcmtPOTriggerModeEJB;

@Stateless
public class ProcmtPOTriggerModeLoader extends AbstractEnumLoader<ProcmtPOTriggerMode> {

	@Inject
	private ProcmtPOTriggerModeEJB ejb;

	@Override
	protected ProcmtPOTriggerMode newObject() {
		return new ProcmtPOTriggerMode();
	}

	public ProcmtPOTriggerMode findByIdentif(String identif) {
		return ejb.findByIdentif(identif);
	}

	public ProcmtPOTriggerMode create(ProcmtPOTriggerMode entity) {
		return ejb.create(entity);
	}

	public ProcmtPOTriggerMode update(ProcmtPOTriggerMode found) {
		return ejb.update(found);
	}

	public ProcmtPOTriggerMode deleteById(String id) {
		return ejb.deleteById(id);
	}

}
