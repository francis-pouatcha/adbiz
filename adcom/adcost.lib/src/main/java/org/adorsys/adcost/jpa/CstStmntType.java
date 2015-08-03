package org.adorsys.adcost.jpa;

import org.adorsys.adcore.annotation.Description;

@Description("CstStmntType_description")
public enum CstStmntType
{
	/*
	 * Assigning cost from the general accounting a primary cost center.
	 */
	@Description("CstStmntType_COST_ASSGMNT_description")
	COST_ASSGMNT,
	/*
	 * Assigning a revenue from the general accounting a primary activity center.
	 */
	@Description("CstStmntType_REVN_ASSGMNT_description")
	REVN_ASSGMNT, 
	/*
	 * Redistributing cost from an activity center to another activity center.
	 */
	@Description("CstStmntType_COST_REDISTN_description")
	COST_REDISTN, 
	/*
	 * Redistributing a revenue from an activity center to another secondary activity center.
	 */
	@Description("CstStmntType_REVN_REDISTN_description")
	REVN_REDISTN, 
	/*
	 * Inventory depreciation. Reduces the value of the inventory, either due to the
	 * expiration, lost, or even change in the market price. Any type or depreciation
	 * on the value of the current inventory.
	 */
	@Description("CstStmntType_INVTRY_DEPRCTN_description")
	INVTRY_DEPRCTN, 
	/*
	 * Inventory appreciation. Increases the value of the inventory, either due to the
	 * recovery, count, or even change in the market price. Any type or appreciation
	 * on the value of the current inventory.
	 */
	@Description("CstStmntType_INVTRY_APPRCTN_description")
	INVTRY_APPRCTN, 
	/*
	 * Profit and lost statement. 
	 */
	@Description("CstStmntType_PANDL_description")
	PANDL,
	/*
	 * MISC 
	 */
	@Description("CstStmntType_MISC_description")
	MISC
	
}