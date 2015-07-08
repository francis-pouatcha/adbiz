package org.adorsys.adsales.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SlsSalesOrderSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<SlsSalesOrder> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private SlsSalesOrderSearchInput searchInput;

   public SlsSalesOrderSearchResult()
   {
      super();
   }

   public SlsSalesOrderSearchResult(Long count, List<SlsSalesOrder> resultList,
         SlsSalesOrderSearchInput searchInput)
   {
      super();
      this.count = count;
      this.resultList = resultList;
      this.searchInput = searchInput;
   }

   public Long getCount()
   {
      return count;
   }

   public List<SlsSalesOrder> getResultList()
   {
      return resultList;
   }

   public SlsSalesOrderSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<SlsSalesOrder> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(SlsSalesOrderSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
