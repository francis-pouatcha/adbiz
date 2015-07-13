package org.adorsys.adcore.jpa;

import org.adorsys.adcore.annotation.Description;


@Description("StkMvtTerminal_description")
public enum CoreStkMvtTerminal
{
   @Description("StkMvtTerminal_WAREHOUSE_description")
   WAREHOUSE, 
   @Description("StkMvtTerminal_POS_description")
   POS,
   @Description("StkMvtTerminal_SUPPLIER_description")
   SUPPLIER, 
   @Description("StkMvtTerminal_CUSTOMER_description")
   CUSTOMER, 
   @Description("StkMvtTerminal_DONNATION_description")
   DONNATION,
   @Description("StkMvtTerminal_SAMPLE_description")
   SAMPLE,
   @Description("StkMvtTerminal_EXPIRING_description")
   EXPIRING,
   @Description("StkMvtTerminal_TRASH_description")
   TRASH,
   @Description("StkMvtTerminal_NONE_description")
   NONE
  
}