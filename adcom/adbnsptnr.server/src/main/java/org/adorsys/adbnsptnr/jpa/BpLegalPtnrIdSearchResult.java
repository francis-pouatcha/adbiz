package org.adorsys.adbnsptnr.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BpLegalPtnrIdSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<BpLegalPtnrId> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private BpLegalPtnrIdSearchInput searchInput;

   public BpLegalPtnrIdSearchResult()
   {
      super();
   }

   public BpLegalPtnrIdSearchResult(Long count, List<BpLegalPtnrId> resultList,
         BpLegalPtnrIdSearchInput searchInput)
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

   public List<BpLegalPtnrId> getResultList()
   {
      return resultList;
   }

   public BpLegalPtnrIdSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<BpLegalPtnrId> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(BpLegalPtnrIdSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
