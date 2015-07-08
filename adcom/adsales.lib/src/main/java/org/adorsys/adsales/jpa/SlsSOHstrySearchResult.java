package org.adorsys.adsales.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SlsSOHstrySearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<SlsSOHstry> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private SlsSOHstrySearchInput searchInput;

   public SlsSOHstrySearchResult()
   {
      super();
   }

   public SlsSOHstrySearchResult(Long count, List<SlsSOHstry> resultList,
         SlsSOHstrySearchInput searchInput)
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

   public List<SlsSOHstry> getResultList()
   {
      return resultList;
   }

   public SlsSOHstrySearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<SlsSOHstry> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(SlsSOHstrySearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
