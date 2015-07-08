package org.adorsys.adinvtry.jpa;

import org.adorsys.javaext.description.Description;

@Description("InvntrStatus_description")
public enum InvInvtryStatus
{
   @Description("InvntrStatus_SUSPENDED_description")
   SUSPENDED, 
   @Description("InvntrStatus_ONGOING_description")
   ONGOING, 
   @Description("InvntrStatus_RESUMED_description")
   RESUMED, 
   @Description("InvntrStatus_CLOSED_description")
   CLOSED,
   @Description("InvntrStatus_INITIALIZING_description")
   INITIALIZING,
   @Description("InvntrStatus_POSTED_description")
   POSTED,
   @Description("InvntrStatus_MERGED_description")
   MERGED
}