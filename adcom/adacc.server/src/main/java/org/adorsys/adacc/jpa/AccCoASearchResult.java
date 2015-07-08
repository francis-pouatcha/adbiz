package org.adorsys.adacc.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AccCoASearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<AccCoA> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private AccCoASearchInput searchInput;

   public AccCoASearchResult()
   {
      super();
   }

   public AccCoASearchResult(Long count, List<AccCoA> resultList,
         AccCoASearchInput searchInput)
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

   public List<AccCoA> getResultList()
   {
      return resultList;
   }

   public AccCoASearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<AccCoA> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(AccCoASearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
