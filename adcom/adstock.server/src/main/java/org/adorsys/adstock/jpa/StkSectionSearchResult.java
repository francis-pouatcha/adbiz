package org.adorsys.adstock.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StkSectionSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<StkSection> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private StkSectionSearchInput searchInput;

   public StkSectionSearchResult()
   {
      super();
   }

   public StkSectionSearchResult(Long count, List<StkSection> resultList,
         StkSectionSearchInput searchInput)
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

   public List<StkSection> getResultList()
   {
      return resultList;
   }

   public StkSectionSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<StkSection> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(StkSectionSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
