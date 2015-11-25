package org.adorsys.adbnsptnr.jpa;

import org.adorsys.adcore.annotation.Description;

@Description("BpInsrncPpseType_description")
public enum BpInsrncPpseType
{
   @Description("BpInsrncPpseType_PURCHASE_OF_MEDECINE_description")
   PURCHASE_OF_MEDECINE, 
   @Description("BpInsrncPpseType_MEDICAL_TREATMENT_description")
   MEDICAL_TREATMENT, 
   @Description("BpInsrncPpseType_DEVICE_REPAIR_description")
   DEVICE_REPAIR, 
   @Description("BpInsrncPpseType_DEVICE_REPLACEMENT_description")
   DEVICE_REPLACEMENT, 
   @Description("BpInsrncPpseType_EMERGENCY_TRANSPORT_description")
   EMERGENCY_TRANSPORT, 
   @Description("BpInsrncPpseType_RESIDUAL_DEBT_description")
   RESIDUAL_DEBT, 
   @Description("BpInsrncPpseType_DAMAGE_COVERAGE_description")
   DAMAGE_COVERAGE
}