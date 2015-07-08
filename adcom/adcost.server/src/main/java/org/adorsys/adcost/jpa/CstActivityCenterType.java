package org.adorsys.adcost.jpa;

import org.adorsys.javaext.description.Description;

@Description("CstActivityCenterType_description")
public enum CstActivityCenterType
{
   @Description("CstActivityCenterType_CAPITAL_ACC_description")
   CAPITAL_ACC, @Description("CstActivityCenterType_ESTATE_ASSETS_ACC_description")
   ESTATE_ASSETS_ACC, @Description("CstActivityCenterType_GENERAL_ASSETS_ACC_description")
   GENERAL_ASSETS_ACC, @Description("CstActivityCenterType_THIRD_PARTY_ACC_description")
   THIRD_PARTY_ACC, @Description("CstActivityCenterType_TREASURY_ACC_description")
   TREASURY_ACC, @Description("CstActivityCenterType_EXPENSE_ACC_description")
   EXPENSE_ACC, @Description("CstActivityCenterType_INCOME_ACC_description")
   INCOME_ACC, @Description("CstActivityCenterType_CLOSING_ACC_description")
   CLOSING_ACC
}