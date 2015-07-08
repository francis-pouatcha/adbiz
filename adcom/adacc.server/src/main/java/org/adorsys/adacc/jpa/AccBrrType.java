package org.adorsys.adacc.jpa;

import org.adorsys.javaext.description.Description;

@Description("AccBrrType_description")
public enum AccBrrType
{
   @Description("AccBrrType_COST_CTR_description")
   COST_CTR, @Description("AccBrrType_COST_BRR_description")
   COST_BRR, @Description("AccBrrType_CUSTOMER_description")
   CUSTOMER, @Description("AccBrrType_SUPPLIER_description")
   SUPPLIER, @Description("AccBrrType_BROKER_description")
   BROKER, @Description("AccBrrType_BUSINESS_PTNR_description")
   BUSINESS_PTNR
}