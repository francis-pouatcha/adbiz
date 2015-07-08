package org.adorsys.adacc.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AccAccountSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<AccAccount> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private AccAccountSearchInput searchInput;

   public AccAccountSearchResult()
   {
      super();
   }

   public AccAccountSearchResult(Long count, List<AccAccount> resultList,
         AccAccountSearchInput searchInput)
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

   public List<AccAccount> getResultList()
   {
      return resultList;
   }

   public AccAccountSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<AccAccount> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(AccAccountSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
