package org.adorsys.adbase.jpa;

import org.adorsys.adcore.annotation.Description;

@Description("PermActionEnum_description")
public enum PermActionEnum
{
   @Description("PermActionEnum_LIST_description")
   LIST, @Description("PermActionEnum_READ_description")
   READ, @Description("PermActionEnum_CREATE_description")
   CREATE, @Description("PermActionEnum_EDIT_description")
   EDIT, @Description("PermActionEnum_ALL_description")
   ALL
}