package org.adorsys.adcshdwr.receiptprint;

import org.adorsys.adbase.jpa.Login;
import org.adorsys.adbase.jpa.OrgUnit;
import org.adorsys.adcshdwr.jpa.CdrDrctSales;
import org.adorsys.adcshdwr.jpa.CdrDsArtItemSearchResult;

/**
 * 
 * @author Hsimo
 * Contains data for printing CdrDrctSales
 *
 */
public class CdrDrctSalesPrinterData {
	
	private final CdrDrctSales cdrDrctSales;
	private final Login login;
	private final OrgUnit organisation;
	private final CdrDsArtItemSearchResult cdrDsArtItemSearchResult;
	
	
	
	public CdrDrctSalesPrinterData(CdrDrctSales cdrDrctSales,
			Login login, OrgUnit organisation,
			CdrDsArtItemSearchResult cdrDsArtItemSearchResult) {
		super();
		this.cdrDrctSales = cdrDrctSales;
		this.login = login;
		this.organisation = organisation;
		this.cdrDsArtItemSearchResult = cdrDsArtItemSearchResult;
	}
	public CdrDrctSales getCdrDrctSales() {
		return cdrDrctSales;
	}
	public Login getLogin() {
		return login;
	}
	public OrgUnit getOrganisation() {
		return organisation;
	}
	public CdrDsArtItemSearchResult getCdrDsArtItemSearchResult() {
		return cdrDsArtItemSearchResult;
	}
	
	

}
