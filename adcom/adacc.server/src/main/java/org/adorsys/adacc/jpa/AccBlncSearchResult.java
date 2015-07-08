package org.adorsys.adacc.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AccBlncSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<AccBlnc> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private AccBlncSearchInput searchInput;

   public AccBlncSearchResult()
   {
      super();
   }

   public AccBlncSearchResult(Long count, List<AccBlnc> resultList,
         AccBlncSearchInput searchInput)
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

   public List<AccBlnc> getResultList()
   {
      return resultList;
   }

   public AccBlncSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<AccBlnc> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(AccBlncSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
