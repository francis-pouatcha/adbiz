package org.adorsys.adcost.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CstActivityCenterSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<CstActivityCenter> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private CstActivityCenterSearchInput searchInput;

   public CstActivityCenterSearchResult()
   {
      super();
   }

   public CstActivityCenterSearchResult(Long count, List<CstActivityCenter> resultList,
         CstActivityCenterSearchInput searchInput)
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

   public List<CstActivityCenter> getResultList()
   {
      return resultList;
   }

   public CstActivityCenterSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<CstActivityCenter> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(CstActivityCenterSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
