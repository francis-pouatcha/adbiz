package org.adorsys.adbase.loader;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.BaseProcStep;
import org.adorsys.adbase.rest.BaseProcStepEJB;
import org.adorsys.adcore.xls.AbstractEnumLoader;

@Stateless
public class BaseProcStepLoader extends AbstractEnumLoader<BaseProcStep> {

	@Inject
	private BaseProcStepEJB ejb;

	@Override
	protected BaseProcStep newObject() {
		return new BaseProcStep();
	}

	public BaseProcStep findByIdentif(String identif) {
		return ejb.findByIdentif(identif);
	}

	public BaseProcStep create(BaseProcStep entity) {
		return ejb.create(entity);
	}

	public BaseProcStep update(BaseProcStep found) {
		return ejb.update(found);
	}

	public BaseProcStep deleteById(String id) {
		return ejb.deleteById(id);
	}

}
