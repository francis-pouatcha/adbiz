package org.adorsys.adbase.loader;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.BaseHistoryType;
import org.adorsys.adbase.rest.BaseHistoryTypeEJB;
import org.adorsys.adcore.xls.AbstractEnumLoader;

@Stateless
public class BaseHistoryTypeLoader extends AbstractEnumLoader<BaseHistoryType> {

	@Inject
	private BaseHistoryTypeEJB ejb;

	@Override
	protected BaseHistoryType newObject() {
		return new BaseHistoryType();
	}

	public BaseHistoryType findByIdentif(String identif) {
		return ejb.findByIdentif(identif);
	}

	public BaseHistoryType create(BaseHistoryType entity) {
		return ejb.create(entity);
	}

	public BaseHistoryType update(BaseHistoryType found) {
		return ejb.update(found);
	}

	public BaseHistoryType deleteById(String id) {
		return ejb.deleteById(id);
	}

}
