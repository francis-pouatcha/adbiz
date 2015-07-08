package org.adorsys.adcshdwr.voucherprint;

import org.adorsys.adbase.jpa.Login;
import org.adorsys.adbase.jpa.OrgUnit;
import org.adorsys.adcshdwr.jpa.CdrCstmrVchr;

public class CdrCstmrVchrPrinterData {
	
	private final CdrCstmrVchr cstmrVchr;
	private final Login login;
	private final OrgUnit company;
	
	public CdrCstmrVchrPrinterData(CdrCstmrVchr cstmrVchr, Login login,
			OrgUnit company) {
		super();
		this.cstmrVchr = cstmrVchr;
		this.login = login;
		this.company = company;
	}

	public CdrCstmrVchr getCstmrVchr() {
		return cstmrVchr;
	}

	public Login getLogin() {
		return login;
	}

	public OrgUnit getCompany() {
		return company;
	}
	
	

}
