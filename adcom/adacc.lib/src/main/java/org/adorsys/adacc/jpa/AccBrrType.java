package org.adorsys.adacc.jpa;

import org.adorsys.javaext.description.Description;

@Description("AccBrrType_description")
public enum AccBrrType
{
	/*
	 * Specified is the target account bearer is a cost center or
	 * an activity center.
	 */
	@Description("AccBrrType_COST_CTR_description")
	COST_CTR,
	/*
	 * Specified if the account bearer is an cost/revenue bearer.
	 */
	@Description("AccBrrType_COST_BRR_description")
	COST_BRR,
	/*
	 * Specified if the bearer of the target account is a customer.
	 */
	@Description("AccBrrType_CUSTOMER_description")
	CUSTOMER, 
	/*
	 * Specified if the bearer of the target account is a supplier.
	 */
	@Description("AccBrrType_SUPPLIER_description")
	SUPPLIER,
	/*
	 * Specified if the bearer of the target account is a broker.
	 */
	@Description("AccBrrType_BROKER_description")
	BROKER, 
	/*
	 * Specified if the bearer of the target account is a business partner. 
	 */
	@Description("AccBrrType_BUSINESS_PTNR_description")
	BUSINESS_PTNR
}