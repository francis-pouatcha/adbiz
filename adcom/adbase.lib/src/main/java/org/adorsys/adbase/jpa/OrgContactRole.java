package org.adorsys.adbase.jpa;

import org.adorsys.adcore.annotation.Description;
@Description("OrgContactRole_description")
public enum OrgContactRole
{
   @Description("OrgContactRole_MAIN_CONTACT_description")
   MAIN_CONTACT, 
   @Description("OrgContactRole_ALT_CONTACT_description")
   ALT_CONTACT, 
   @Description("OrgContactRole_OFFICE_ADDRESS_description")
   HOME_ADDRESS, 
   @Description("OrgContactRole_HOME_ADDRESS_description")
   PRIVATE_ADDRESS, 
   @Description("OrgContactRole_DELIVERY_ADDRESS_description")
   DELIVERY_ADDRESS, 
   @Description("OrgContactRole_INVOICE_ADDRESS_description")
   INVOICE_ADDRESS, 
   @Description("OrgContactRole_SUPPORT_CONTACT_description")
   SUPPORT_CONTACT, 
   @Description("OrgContactRole_EMERGENCY_CONTACT_description")
   EMERGENCY_CONTACT
}