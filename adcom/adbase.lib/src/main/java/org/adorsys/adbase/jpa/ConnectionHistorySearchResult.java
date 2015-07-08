package org.adorsys.adbase.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ConnectionHistorySearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<ConnectionHistory> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private ConnectionHistorySearchInput searchInput;

   public ConnectionHistorySearchResult()
   {
      super();
   }

   public ConnectionHistorySearchResult(Long count, List<ConnectionHistory> resultList,
         ConnectionHistorySearchInput searchInput)
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

   public List<ConnectionHistory> getResultList()
   {
      return resultList;
   }

   public ConnectionHistorySearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<ConnectionHistory> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(ConnectionHistorySearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
