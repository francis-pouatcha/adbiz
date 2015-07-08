package org.adorsys.adbnsptnr.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BpCtgryOfPtnrSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<BpCtgryOfPtnr> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private BpCtgryOfPtnrSearchInput searchInput;

   public BpCtgryOfPtnrSearchResult()
   {
      super();
   }

   public BpCtgryOfPtnrSearchResult(Long count, List<BpCtgryOfPtnr> resultList,
         BpCtgryOfPtnrSearchInput searchInput)
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

   public List<BpCtgryOfPtnr> getResultList()
   {
      return resultList;
   }

   public BpCtgryOfPtnrSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<BpCtgryOfPtnr> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(BpCtgryOfPtnrSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
