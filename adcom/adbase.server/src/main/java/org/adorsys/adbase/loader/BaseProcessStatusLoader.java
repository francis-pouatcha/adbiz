package org.adorsys.adbase.loader;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.BaseProcessStatus;
import org.adorsys.adbase.rest.BaseProcessStatusEJB;
import org.adorsys.adcore.xls.AbstractEnumLoader;

@Stateless
public class BaseProcessStatusLoader extends AbstractEnumLoader<BaseProcessStatus> {

	@Inject
	private BaseProcessStatusEJB ejb;

	@Override
	protected BaseProcessStatus newObject() {
		return new BaseProcessStatus();
	}

	public BaseProcessStatus findByIdentif(String identif) {
		return ejb.findByIdentif(identif);
	}

	public BaseProcessStatus create(BaseProcessStatus entity) {
		return ejb.create(entity);
	}

	public BaseProcessStatus update(BaseProcessStatus found) {
		return ejb.update(found);
	}

	public BaseProcessStatus deleteById(String id) {
		return ejb.deleteById(id);
	}

}
