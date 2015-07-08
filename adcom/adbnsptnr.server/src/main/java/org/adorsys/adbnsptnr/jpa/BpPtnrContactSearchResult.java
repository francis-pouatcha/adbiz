package org.adorsys.adbnsptnr.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BpPtnrContactSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<BpPtnrContact> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private BpPtnrContactSearchInput searchInput;

   public BpPtnrContactSearchResult()
   {
      super();
   }

   public BpPtnrContactSearchResult(Long count, List<BpPtnrContact> resultList,
         BpPtnrContactSearchInput searchInput)
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

   public List<BpPtnrContact> getResultList()
   {
      return resultList;
   }

   public BpPtnrContactSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<BpPtnrContact> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(BpPtnrContactSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
