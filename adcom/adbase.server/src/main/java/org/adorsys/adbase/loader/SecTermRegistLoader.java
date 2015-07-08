package org.adorsys.adbase.loader;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.SecTermCredtl;
import org.adorsys.adbase.jpa.SecTermRegist;
import org.adorsys.adbase.rest.SecTermCredtlEJB;
import org.adorsys.adbase.rest.SecTermRegistEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;
import org.apache.commons.lang3.time.DateUtils;

@Stateless
public class SecTermRegistLoader extends AbstractObjectLoader<SecTermRegist> {

	@Inject
	private SecTermRegistEJB ejb;

	@Override
	protected SecTermRegist newObject() {
		return new SecTermRegist();
	}

	public SecTermRegist findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	public SecTermRegist create(SecTermRegist entity) {
		return ejb.create(entity);
	}

	public SecTermRegist update(SecTermRegist found) {
		return ejb.update(found);
	}

	public SecTermRegist deleteById(String id) {
		return ejb.deleteById(id);
	}

	@Inject
	private SecTermCredtlEJB termCredtlEJB;

	@PostConstruct
	public void postConstruct(){
		String testTermCredId = "67a7723c-83b1-446a-bf96-f536036dedb4";
		SecTermCredtl termCredtl = termCredtlEJB.findById(testTermCredId);
		if(termCredtl!=null) return;
		termCredtl = new SecTermCredtl();
		termCredtl.setId(testTermCredId);
		termCredtl.setCreated(new Date());
		termCredtl.setExpires(DateUtils.addYears(new Date(), 1));
		termCredtl.setLangIso2("en");
		termCredtl.setTermId("A01CMTest_20150328150924");
		termCredtl.setTermName("A01CMTest");
		termCredtlEJB.create(termCredtl);
	}
}
