package org.adorsys.adprocmt.loader;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.xls.AbstractEnumLoader;
import org.adorsys.adprocmt.jpa.ProcmtPOType;
import org.adorsys.adprocmt.rest.ProcmtPOTypeEJB;

@Stateless
public class ProcmtPOTypeLoader extends AbstractEnumLoader<ProcmtPOType> {

	@Inject
	private ProcmtPOTypeEJB ejb;

	@Override
	protected ProcmtPOType newObject() {
		return new ProcmtPOType();
	}

	public ProcmtPOType findByIdentif(String identif) {
		return ejb.findByIdentif(identif);
	}

	public ProcmtPOType create(ProcmtPOType entity) {
		return ejb.create(entity);
	}

	public ProcmtPOType update(ProcmtPOType found) {
		return ejb.update(found);
	}

	public ProcmtPOType deleteById(String id) {
		return ejb.deleteById(id);
	}

}
