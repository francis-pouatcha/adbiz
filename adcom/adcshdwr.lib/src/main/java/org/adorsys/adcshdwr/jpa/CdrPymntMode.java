package org.adorsys.adcshdwr.jpa;

import org.adorsys.javaext.description.Description;

@Description("CdrPymntMode_description")
public enum CdrPymntMode
{
   @Description("CdrPymntMode_CASH_description")
   CASH, @Description("CdrPymntMode_CHECK_description")
   CHECK, @Description("CdrPymntMode_CREDIT_CARD_description")
   CREDIT_CARD, @Description("CdrPymntMode_VOUCHER_description")
   VOUCHER
}