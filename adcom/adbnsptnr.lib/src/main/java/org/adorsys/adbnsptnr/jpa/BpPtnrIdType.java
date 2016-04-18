package org.adorsys.adbnsptnr.jpa;

import org.adorsys.adcore.annotation.Description;

@Description("BpPtnrIdType_description")
public enum BpPtnrIdType
{
   @Description("BpPtnrIdType_IDCARDNBR_description")
   IDCARDNBR, @Description("BpPtnrIdType_RESDTCARDNBR_description")
   RESDTCARDNBR, @Description("BpPtnrIdType_DRIVERLICNBR_description")
   DRIVERLICNBR, @Description("BpPtnrIdType_PASSPORTNBR_description")
   PASSPORTNBR, @Description("BpPtnrIdType_EMPLOYEENBR_description")
   EMPLOYEENBR, @Description("BpPtnrIdType_MEMBERSHIP_description")
   MEMBERSHIP, @Description("BpPtnrIdType_INSURER_description")
   INSURER, @Description("BpPtnrIdType_INSURED_description")
   INSURED
}