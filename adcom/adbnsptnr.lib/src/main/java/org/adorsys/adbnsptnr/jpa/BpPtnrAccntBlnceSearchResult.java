package org.adorsys.adbnsptnr.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BpPtnrAccntBlnceSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<BpPtnrAccntBlnce> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private BpPtnrAccntBlnceSearchInput searchInput;

   public BpPtnrAccntBlnceSearchResult()
   {
      super();
   }

   public BpPtnrAccntBlnceSearchResult(Long count, List<BpPtnrAccntBlnce> resultList,
         BpPtnrAccntBlnceSearchInput searchInput)
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

   public List<BpPtnrAccntBlnce> getResultList()
   {
      return resultList;
   }

   public BpPtnrAccntBlnceSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<BpPtnrAccntBlnce> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(BpPtnrAccntBlnceSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
