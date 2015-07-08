package org.adorsys.adbase.loader;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.SecTerminal;
import org.adorsys.adbase.rest.SecTerminalEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class SecTerminalLoader extends AbstractObjectLoader<SecTerminal> {

	@Inject
	private SecTerminalEJB ejb;

	@Override
	protected SecTerminal newObject() {
		return new SecTerminal();
	}

	public SecTerminal findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	public SecTerminal create(SecTerminal entity) {
		return ejb.create(entity);
	}

	public SecTerminal update(SecTerminal found) {
		return ejb.update(found);
	}

	public SecTerminal deleteById(String id) {
		return ejb.deleteById(id);
	}

	@PostConstruct
	public void postConstruct(){
		String testTermId = "A01CMTest_20150328150924";
		SecTerminal term = ejb.findById(testTermId);
		if(term!=null) return;
		term = new SecTerminal();
		term.setId(testTermId);
		term.setIdentif("A01CMTest");
		term.setValidFrom(new Date());
		term.setAuthUsers("test");
		term.setLangIso2("fr");
		term.setPartnerIds("A01CM");
		term.setTermId("A01CMTest");
		term.setTermName("A01CMTest");
		term.setTimeZone("Africa/Douala");
		term.setAuthWorkspaces("A01CM_cashier_A01CM,A01CM_admin_A01CM,A01CM_stocks_A01CM,A01CM_catal_A01CM,A01CM_procmt_A01CM,A01CM_invtry_A01CM,A01CM_bnsptnr_A01CM,A01CM_sales_A01CM,A01CM_accounting_A01CM");
		ejb.create(term);
	}
}
