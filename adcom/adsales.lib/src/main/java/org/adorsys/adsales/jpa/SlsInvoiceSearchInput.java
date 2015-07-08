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
public class SlsInvoiceSearchInput
{

   /**
    * The entity holding search inputs.
    */
   private SlsInvoice entity;

   /**
    * The start cursor
    */
   private int start = -1;

   /**
    * The max number of records to return.
    */
   private int max = -1;
   
   private Date invceDtFrom;
   
   private Date invceDtTo;
   
   private String ptnrNbr;
   
   
/**
    * The field names to be included in the search.
    */
   private List<String> fieldNames = new ArrayList<String>();

   public SlsInvoice getEntity()
   {
      return entity;
   }

   public void setEntity(SlsInvoice entity)
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

	public Date getInvceDtFrom() {
		return invceDtFrom;
	}
	
	public void setInvceDtFrom(Date invceDtFrom) {
		this.invceDtFrom = invceDtFrom;
	}
	
	public Date getInvceDtTo() {
		return invceDtTo;
	}
	
	public void setInvceDtTo(Date invceDtTo) {
		this.invceDtTo = invceDtTo;
	}
	
	public String getPtnrNbr() {
		return ptnrNbr;
	}
	
	public void setPtnrNbr(String ptnrNbr) {
		this.ptnrNbr = ptnrNbr;
	}
   
   
	public boolean noSpecialParams(){
		return invceDtFrom==null && invceDtTo==null && ptnrNbr==null;
	}
   
   
}
