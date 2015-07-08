package org.adorsys.adsales.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SlsSOPtnrSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<SlsSOPtnr> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private SlsSOPtnrSearchInput searchInput;

   public SlsSOPtnrSearchResult()
   {
      super();
   }

   public SlsSOPtnrSearchResult(Long count, List<SlsSOPtnr> resultList,
         SlsSOPtnrSearchInput searchInput)
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

   public List<SlsSOPtnr> getResultList()
   {
      return resultList;
   }

   public SlsSOPtnrSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<SlsSOPtnr> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(SlsSOPtnrSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
