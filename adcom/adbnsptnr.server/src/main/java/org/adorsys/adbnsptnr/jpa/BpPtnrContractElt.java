package org.adorsys.adbnsptnr.jpa;

import org.adorsys.javaext.description.Description;

@Description("BpPtnrContractElt_description")
public enum BpPtnrContractElt {
	@Description("BpPtnrContractElt_SALES_DISCOUNT_description")
	SALES_DISCOUNT, 
	@Description("BpPtnrContractElt_PURCHASE_DISCOUNT_description")
	PURCHASE_DISCOUNT, 
	@Description("BpPtnrContractElt_ROYALTY_DISCOUNT_description")
	ROYALTY_DISCOUNT, 
	@Description("BpPtnrContractElt_QUANTITY_DISCOUNT_description")
	QUANTITY_DISCOUNT, 
	@Description("BpPtnrContractElt_PAYMENT_DISCOUNT_description")
	PAYMENT_DISCOUNT, 
	@Description("BpPtnrContractElt_WAIVER_DISCOUNT_description")
	WAIVER_DISCOUNT, 
	@Description("BpPtnrContractElt_QUANTITY_DISCOUNT_description")
	SALES_COMMISSION, 
	@Description("BpPtnrContractElt_PURCHASE_COMMISSION_description")
	PURCHASE_COMMISSION, 
	@Description("BpPtnrContractElt_BROKERAGE_COMMISSION_description")
	BROKERAGE_COMMISSION, 
	@Description("BpPtnrContractElt_COLLECTION_COMMISSION_description")
	COLLECTION_COMMISSION, 
	@Description("BpPtnrContractElt_PAYMENT_COMMISSION_description")
	PAYMENT_COMMISSION, 
	@Description("BpPtnrContractElt_REIMBURSEMENT_COMMISSION_description")
	REIMBURSEMENT_COMMISSION, 
	@Description("BpPtnrContractElt_ANNULATION_COMMISSION_description")
	ANNULATION_COMMISSION, 
	@Description("BpPtnrContractElt_SERVICE_COMMISSION_description")
	SERVICE_COMMISSION, 
	@Description("BpPtnrContractElt_SERVICE_FEES_description")
	SERVICE_FEES, 
	@Description("BpPtnrContractElt_RESTOCKING_FEES_description")
	RESTOCKING_FEES, 
	@Description("BpPtnrContractElt_HANDLING_FEES_description")
	HANDLING_FEES, 
	@Description("BpPtnrContractElt_MANAGEMENT_FEES_description")
	MANAGEMENT_FEES
}