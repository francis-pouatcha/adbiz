package org.adorsys.adbnsptnr.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BpPtnrCtgryDtlsSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<BpPtnrCtgryDtls> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private BpPtnrCtgryDtlsSearchInput searchInput;

   public BpPtnrCtgryDtlsSearchResult()
   {
      super();
   }

   public BpPtnrCtgryDtlsSearchResult(Long count, List<BpPtnrCtgryDtls> resultList,
         BpPtnrCtgryDtlsSearchInput searchInput)
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

   public List<BpPtnrCtgryDtls> getResultList()
   {
      return resultList;
   }

   public BpPtnrCtgryDtlsSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<BpPtnrCtgryDtls> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(BpPtnrCtgryDtlsSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
