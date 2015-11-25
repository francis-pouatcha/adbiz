package org.adorsys.adbnsptnr.jpa;

import org.adorsys.adcore.annotation.Description;

@Description("BpPtnrRole_description")
public enum BpPtnrRole
{
   @Description("BpPtnrRole_CUSTOMER_description")
   CUSTOMER, @Description("BpPtnrRole_SUPPLIER_description")
   SUPPLIER, @Description("BpPtnrRole_EMPLOYER_description")
   EMPLOYER, @Description("BpPtnrRole_STAFF_description")
   STAFF, @Description("BpPtnrRole_MANUFACTURER_description")
   MANUFACTURER, @Description("BpPtnrRole_GOVERNMENT_description")
   GOVERNMENT, @Description("BpPtnrRole_BROKER_description")
   BROKER, @Description("BpPtnrRole_SHAREHOLDER_description")
   SHAREHOLDER, @Description("BpPtnrRole_BANKER_description")
   BANKER, @Description("BpPtnrRole_INSURANCE_description")
   INSURANCE
}