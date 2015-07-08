package org.adorsys.adbnsptnr.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BpPtnrCtgrySearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<BpPtnrCtgry> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private BpPtnrCtgrySearchInput searchInput;

   public BpPtnrCtgrySearchResult()
   {
      super();
   }

   public BpPtnrCtgrySearchResult(Long count, List<BpPtnrCtgry> resultList,
         BpPtnrCtgrySearchInput searchInput)
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

   public List<BpPtnrCtgry> getResultList()
   {
      return resultList;
   }

   public BpPtnrCtgrySearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<BpPtnrCtgry> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(BpPtnrCtgrySearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
