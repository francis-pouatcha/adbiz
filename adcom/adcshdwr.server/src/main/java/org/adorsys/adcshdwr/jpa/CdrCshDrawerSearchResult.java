package org.adorsys.adcshdwr.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CdrCshDrawerSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<CdrCshDrawer> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private CdrCshDrawerSearchInput searchInput;

   public CdrCshDrawerSearchResult()
   {
      super();
   }

   public CdrCshDrawerSearchResult(Long count, List<CdrCshDrawer> resultList,
         CdrCshDrawerSearchInput searchInput)
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

   public List<CdrCshDrawer> getResultList()
   {
      return resultList;
   }

   public CdrCshDrawerSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<CdrCshDrawer> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(CdrCshDrawerSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
