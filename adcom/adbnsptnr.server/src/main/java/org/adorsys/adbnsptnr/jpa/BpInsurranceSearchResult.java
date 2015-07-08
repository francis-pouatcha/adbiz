package org.adorsys.adbnsptnr.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BpInsurranceSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<BpInsurrance> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private BpInsurranceSearchInput searchInput;

   public BpInsurranceSearchResult()
   {
      super();
   }

   public BpInsurranceSearchResult(Long count, List<BpInsurrance> resultList,
         BpInsurranceSearchInput searchInput)
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

   public List<BpInsurrance> getResultList()
   {
      return resultList;
   }

   public BpInsurranceSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<BpInsurrance> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(BpInsurranceSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
