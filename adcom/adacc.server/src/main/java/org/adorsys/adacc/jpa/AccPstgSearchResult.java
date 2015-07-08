package org.adorsys.adacc.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AccPstgSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<AccPstg> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private AccPstgSearchInput searchInput;

   public AccPstgSearchResult()
   {
      super();
   }

   public AccPstgSearchResult(Long count, List<AccPstg> resultList,
         AccPstgSearchInput searchInput)
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

   public List<AccPstg> getResultList()
   {
      return resultList;
   }

   public AccPstgSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<AccPstg> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(AccPstgSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
