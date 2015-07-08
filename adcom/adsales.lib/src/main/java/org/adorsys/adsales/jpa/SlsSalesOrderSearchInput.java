package org.adorsys.adsales.jpa;

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
public class SlsSalesOrderSearchInput
{

   /**
    * The entity holding search inputs.
    */
   private SlsSalesOrder entity;

   /**
    * The start cursor
    */
   private int start = -1;

   /**
    * The max number of records to return.
    */
   private int max = -1;
   
   private Date slsSODtFrom;
   
   private Date slsSODtTo;
   
   private String ptnrNbr;
   

/**
    * The field names to be included in the search.
    */
   private List<String> fieldNames = new ArrayList<String>();

   public SlsSalesOrder getEntity()
   {
      return entity;
   }

   public void setEntity(SlsSalesOrder entity)
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
   
   

	public Date getSlsSODtFrom() {
		return slsSODtFrom;
	}
	
	public void setSlsSODtFrom(Date slsSODtFrom) {
		this.slsSODtFrom = slsSODtFrom;
	}
	
	public Date getSlsSODtTo() {
		return slsSODtTo;
	}
	
	public void setSlsSODtTo(Date slsSODtTo) {
		this.slsSODtTo = slsSODtTo;
    }

	public String getPtnrNbr() {
		return ptnrNbr;
	}

	public void setPtnrNbr(String ptnrNbr) {
		this.ptnrNbr = ptnrNbr;
	}
	
	public boolean noSpecialParams(){
		return slsSODtFrom==null && slsSODtTo==null && ptnrNbr==null;
	}
}
