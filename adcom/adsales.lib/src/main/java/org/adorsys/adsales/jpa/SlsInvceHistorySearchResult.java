package org.adorsys.adsales.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SlsInvceHistorySearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<SlsInvceHistory> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private SlsInvceHistorySearchInput searchInput;

   public SlsInvceHistorySearchResult()
   {
      super();
   }

   public SlsInvceHistorySearchResult(Long count, List<SlsInvceHistory> resultList,
         SlsInvceHistorySearchInput searchInput)
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

   public List<SlsInvceHistory> getResultList()
   {
      return resultList;
   }

   public SlsInvceHistorySearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<SlsInvceHistory> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(SlsInvceHistorySearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
