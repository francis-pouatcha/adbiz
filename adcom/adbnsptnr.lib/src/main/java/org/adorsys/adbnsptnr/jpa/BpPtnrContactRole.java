package org.adorsys.adbnsptnr.jpa;

import org.adorsys.adcore.annotation.Description;
@Description("BpPtnrContactRole_description")
public enum BpPtnrContactRole
{
   @Description("BpPtnrContactRole_MAIN_CONTACT_description")
   MAIN_CONTACT, 
   @Description("BpPtnrContactRole_ALT_CONTACT_description")
   ALT_CONTACT, 
   @Description("BpPtnrContactRole_OFFICE_ADDRESS_description")
   HOME_ADDRESS, 
   @Description("BpPtnrContactRole_HOME_ADDRESS_description")
   PRIVATE_ADDRESS, 
   @Description("BpPtnrContactRole_DELIVERY_ADDRESS_description")
   DELIVERY_ADDRESS, 
   @Description("BpPtnrContactRole_INVOICE_ADDRESS_description")
   INVOICE_ADDRESS, 
   @Description("BpPtnrContactRole_SUPPORT_CONTACT_description")
   SUPPORT_CONTACT, 
   @Description("BpPtnrContactRole_EMERGENCY_CONTACT_description")
   EMERGENCY_CONTACT
}