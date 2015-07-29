package org.adorsys.adcshdwr.voucherprint;

import org.adorsys.adbase.jpa.Login;
import org.adorsys.adbase.jpa.OrgUnit;

/**
 * 
 * @author Hsimo
 * The data for print voucher pdf
 *
 */
public class VoucherprinterData {
	
	private final String customer;
	private final String voucherDate;
	private final Login login;
	private final OrgUnit company;
	
	public VoucherprinterData(String customer, String voucherDate, Login login,
			OrgUnit company) {
		super();
		this.customer = customer;
		this.voucherDate = voucherDate;
		this.login = login;
		this.company = company;
	}

	public String getCustomer() {
		return customer;
	}

	public String getVoucherDate() {
		return voucherDate;
	}

	public Login getLogin() {
		return login;
	}

	public OrgUnit getCompany() {
		return company;
	}
	
	

}
