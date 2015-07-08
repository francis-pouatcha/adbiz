package org.adorsys.adbase.loader;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.BaseDocType;
import org.adorsys.adbase.rest.BaseDocTypeEJB;
import org.adorsys.adcore.xls.AbstractEnumLoader;

@Stateless
public class BaseDocTypeLoader extends AbstractEnumLoader<BaseDocType> {

	@Inject
	private BaseDocTypeEJB ejb;

	@Override
	protected BaseDocType newObject() {
		return new BaseDocType();
	}

	public BaseDocType findByIdentif(String identif) {
		return ejb.findByIdentif(identif);
	}

	public BaseDocType create(BaseDocType entity) {
		return ejb.create(entity);
	}

	public BaseDocType update(BaseDocType found) {
		return ejb.update(found);
	}

	public BaseDocType deleteById(String id) {
		return ejb.deleteById(id);
	}

}
