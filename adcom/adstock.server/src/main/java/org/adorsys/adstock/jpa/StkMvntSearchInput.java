package org.adorsys.adstock.jpa;

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
public class StkMvntSearchInput
{

   /**
    * The entity holding search inputs.
    */
   private StkMvnt entity;

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
   
   
   private Date mvntDateFrom;
   
   private Date mvntDateTo;
   
   private String artName;
   
   
   public String getArtName() {
	return artName;
   }
   
   public void setArtName(String artName) {
	this.artName = artName;
   }
   
   public Date getMvntDateFrom() {
	return mvntDateFrom;
   }
   
   public Date getMvntDateTo() {
	return mvntDateTo;
   }
   
   public void setMvntDateFrom(Date mvntDateFrom) {
	this.mvntDateFrom = mvntDateFrom;
   }
   
   public void setMvntDateTo(Date mvntDateTo) {
	this.mvntDateTo = mvntDateTo;
   }

   public StkMvnt getEntity()
   {
      return entity;
   }

   public void setEntity(StkMvnt entity)
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
}
