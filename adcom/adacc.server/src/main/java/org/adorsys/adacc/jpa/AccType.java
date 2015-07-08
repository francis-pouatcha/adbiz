package org.adorsys.adacc.jpa;

import org.adorsys.javaext.description.Description;

@Description("AccType_description")
public enum AccType
{
   @Description("AccType_CAPITAL_ACC_description")
   CAPITAL_ACC, @Description("AccType_ESTATE_ASSETS_ACC_description")
   ESTATE_ASSETS_ACC, @Description("AccType_GENERAL_ASSETS_ACC_description")
   GENERAL_ASSETS_ACC, @Description("AccType_THIRD_PARTY_ACC_description")
   THIRD_PARTY_ACC, @Description("AccType_TREASURY_ACC_description")
   TREASURY_ACC, @Description("AccType_EXPENSE_ACC_description")
   EXPENSE_ACC, @Description("AccType_INCOME_ACC_description")
   INCOME_ACC, @Description("AccType_CLOSING_ACC_description")
   CLOSING_ACC
}