package org.adorsys.adbase.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.BaseCountryName;
import org.adorsys.adbase.rest.BaseCountryNameEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class BaseCountryNameLoader extends AbstractObjectLoader<BaseCountryName> {

	@Inject
	private BaseCountryNameEJB ejb;

	@Override
	protected BaseCountryName newObject() {
		return new BaseCountryName();
	}

	public BaseCountryName findByIdentif(String identif, Date validOn) {
		return ejb.findById(identif);
	}

	public BaseCountryName create(BaseCountryName entity) {
		return ejb.create(entity);
	}

	public BaseCountryName update(BaseCountryName found) {
		return ejb.update(found);
	}

	public BaseCountryName deleteById(String id) {
		return ejb.deleteById(id);
	}

}
