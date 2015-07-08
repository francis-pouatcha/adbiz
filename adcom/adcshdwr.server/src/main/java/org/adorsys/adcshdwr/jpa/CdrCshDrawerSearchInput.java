package org.adorsys.adcshdwr.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Holds an entity and corresponding field descriptions 
 * for a search by example call.
 * 
 * @author francis pouatcha
 *
 */
@XmlRootElement
public class CdrCshDrawerSearchInput
{

   /**
    * The entity holding search inputs.
    */
   private CdrCshDrawer entity;
   
   private Date from;
   
   private Date to;

   /**
    * The start cursor
    */
   private int start = -1;

   /**
    * The max number of records to return.
    */
   private int max = -1;

   /**
    * The field names to be included in the search.
    */
   private List<String> fieldNames = new ArrayList<String>();

   public CdrCshDrawer getEntity()
   {
      return entity;
   }

   public void setEntity(CdrCshDrawer entity)
   {
      this.entity = entity;
   }

   public List<String> getFieldNames()
   {
      return fieldNames;
   }

   public void setFieldNames(List<String> fieldNames)
   {
      this.fieldNames = fieldNames;
   }

   public int getStart()
   {
      return start;
   }

   public Date getFrom() {
	return from;
}

public void setFrom(Date from) {
	this.from = from;
}

public Date getTo() {
	return to;
}

public void setTo(Date to) {
	this.to = to;
}

public void setStart(int start)
   {
      this.start = start;
   }

   public int getMax()
   {
      return max;
   }

   public void setMax(int max)
   {
      this.max = max;
   }
}
