package org.adorsys.adbnsptnr.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BpPtnrIdDtlsSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<BpPtnrIdDtls> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private BpPtnrIdDtlsSearchInput searchInput;

   public BpPtnrIdDtlsSearchResult()
   {
      super();
   }

   public BpPtnrIdDtlsSearchResult(Long count, List<BpPtnrIdDtls> resultList,
         BpPtnrIdDtlsSearchInput searchInput)
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

   public List<BpPtnrIdDtls> getResultList()
   {
      return resultList;
   }

   public BpPtnrIdDtlsSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<BpPtnrIdDtls> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(BpPtnrIdDtlsSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
