package org.adorsys.adbase.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.Country;
import org.adorsys.adbase.rest.CountryEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class CountryLoader extends AbstractObjectLoader<Country> {

	@Inject
	private CountryEJB ejb;
	
	@Override
	protected Country newObject() {
		return new Country();
	}

	public Country findByIdentif(String identif, Date validOn){return ejb.findByIdentif(identif, validOn);}
	public Country create(Country entity){return ejb.create(entity);}
	public Country update(Country found){return ejb.update(found);}
	public Country deleteById(String id){return ejb.deleteById(id);}
}
