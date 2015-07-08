package org.adorsys.adbase.loader;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.BaseSttlmtOp;
import org.adorsys.adbase.rest.BaseSttlmtOpEJB;
import org.adorsys.adcore.xls.AbstractEnumLoader;

@Stateless
public class BaseSttlmtOpLoader extends AbstractEnumLoader<BaseSttlmtOp> {

	@Inject
	private BaseSttlmtOpEJB ejb;

	@Override
	protected BaseSttlmtOp newObject() {
		return new BaseSttlmtOp();
	}

	public BaseSttlmtOp findByIdentif(String identif) {
		return ejb.findByIdentif(identif);
	}

	public BaseSttlmtOp create(BaseSttlmtOp entity) {
		return ejb.create(entity);
	}

	public BaseSttlmtOp update(BaseSttlmtOp found) {
		return ejb.update(found);
	}

	public BaseSttlmtOp deleteById(String id) {
		return ejb.deleteById(id);
	}

}
