package org.adorsys.adbnsptnr.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BpPtnrContractSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<BpPtnrContract> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private BpPtnrContractSearchInput searchInput;

   public BpPtnrContractSearchResult()
   {
      super();
   }

   public BpPtnrContractSearchResult(Long count, List<BpPtnrContract> resultList,
         BpPtnrContractSearchInput searchInput)
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

   public List<BpPtnrContract> getResultList()
   {
      return resultList;
   }

   public BpPtnrContractSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<BpPtnrContract> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(BpPtnrContractSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
