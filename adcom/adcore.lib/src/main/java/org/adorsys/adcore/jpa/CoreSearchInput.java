package org.adorsys.adcore.jpa;

import java.util.ArrayList;
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
public class CoreSearchInput<T>
{
	public static final int MAX_MAX = 200;

   /**
    * The entity holding search inputs.
    */
   private T entity;

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

   public T getEntity()
   {
      return entity;
   }

   public void setEntity(T entity)
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
      this.max = checkMax(max);
   }
   
   public static final int checkMax(int max){
	   if(max <=0 || max>MAX_MAX) throw new IllegalArgumentException("Max must be more than 0 less than or equals: " + MAX_MAX);
	   return max;
   }
}
