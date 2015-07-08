package org.adorsys.adacc.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AccBrrSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<AccBrr> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private AccBrrSearchInput searchInput;

   public AccBrrSearchResult()
   {
      super();
   }

   public AccBrrSearchResult(Long count, List<AccBrr> resultList,
         AccBrrSearchInput searchInput)
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

   public List<AccBrr> getResultList()
   {
      return resultList;
   }

   public AccBrrSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<AccBrr> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(AccBrrSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
