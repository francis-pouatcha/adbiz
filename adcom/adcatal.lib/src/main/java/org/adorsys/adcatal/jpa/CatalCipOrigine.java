package org.adorsys.adcatal.jpa;

import org.adorsys.adcore.annotation.Description;

@Description("CatalCipOrigine_description")
public enum CatalCipOrigine {

	@Description("CatalCipOrigine_MAIN_description")
	MAIN, 
	@Description("CatalCipOrigine_SUPPLIER_description")
	SUPPLIER, 
	@Description("CatalCipOrigine_DETAIL_description")
	DETAIL,
	@Description("CatalCipOrigine_MANUFACTURER_description")
	MANUFACTURER,
	@Description("CatalCipOrigine_RESALER_description")
	RESALER,
	@Description("CatalCipOrigine_GOVERNMENT_description")
	GOVERNMENT,
	@Description("CatalCipOrigine_BROKER_description")
	BROKER,
	@Description("CatalCipOrigine_CUSTOMERSERVICE_description")
	CUSTOMERSERVICE,
	@Description("CatalCipOrigine_INSURANCE_description")
	INSURANCE
}