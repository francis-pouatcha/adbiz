package org.adorsys.adsales.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SlsAcctSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<SlsAcct> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private SlsAcctSearchInput searchInput;

   public SlsAcctSearchResult()
   {
      super();
   }

   public SlsAcctSearchResult(Long count, List<SlsAcct> resultList,
         SlsAcctSearchInput searchInput)
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

   public List<SlsAcct> getResultList()
   {
      return resultList;
   }

   public SlsAcctSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<SlsAcct> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(SlsAcctSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
