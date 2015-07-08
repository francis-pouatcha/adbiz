package org.adorsys.adbase.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.ConverterCurrRate;
import org.adorsys.adbase.rest.ConverterCurrRateEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class ConverterCurrRateLoader extends AbstractObjectLoader<ConverterCurrRate> {

	@Inject
	private ConverterCurrRateEJB ejb;
	
	@Override
	protected ConverterCurrRate newObject() {
		return new ConverterCurrRate();
	}

	public ConverterCurrRate findByIdentif(String identif, Date validOn){return ejb.findByIdentif(identif, validOn);}
	public ConverterCurrRate create(ConverterCurrRate entity){return ejb.create(entity);}
	public ConverterCurrRate update(ConverterCurrRate found){return ejb.update(found);}
	public ConverterCurrRate deleteById(String id){return ejb.deleteById(id);}
	
}
