package org.adorsys.adsales.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SlsInvcePtnrSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<SlsInvcePtnr> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private SlsInvcePtnrSearchInput searchInput;

   public SlsInvcePtnrSearchResult()
   {
      super();
   }

   public SlsInvcePtnrSearchResult(Long count, List<SlsInvcePtnr> resultList,
         SlsInvcePtnrSearchInput searchInput)
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

   public List<SlsInvcePtnr> getResultList()
   {
      return resultList;
   }

   public SlsInvcePtnrSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<SlsInvcePtnr> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(SlsInvcePtnrSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
