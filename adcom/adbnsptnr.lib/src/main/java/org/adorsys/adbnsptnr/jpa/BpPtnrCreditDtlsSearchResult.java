package org.adorsys.adbnsptnr.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BpPtnrCreditDtlsSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<BpPtnrCreditDtls> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private BpPtnrCreditDtlsSearchInput searchInput;

   public BpPtnrCreditDtlsSearchResult()
   {
      super();
   }

   public BpPtnrCreditDtlsSearchResult(Long count, List<BpPtnrCreditDtls> resultList,
         BpPtnrCreditDtlsSearchInput searchInput)
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

   public List<BpPtnrCreditDtls> getResultList()
   {
      return resultList;
   }

   public BpPtnrCreditDtlsSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<BpPtnrCreditDtls> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(BpPtnrCreditDtlsSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
