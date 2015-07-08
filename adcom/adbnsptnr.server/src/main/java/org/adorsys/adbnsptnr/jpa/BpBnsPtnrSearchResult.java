package org.adorsys.adbnsptnr.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BpBnsPtnrSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<BpBnsPtnr> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private BpBnsPtnrSearchInput searchInput;

   public BpBnsPtnrSearchResult()
   {
      super();
   }

   public BpBnsPtnrSearchResult(Long count, List<BpBnsPtnr> resultList,
         BpBnsPtnrSearchInput searchInput)
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

   public List<BpBnsPtnr> getResultList()
   {
      return resultList;
   }

   public BpBnsPtnrSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<BpBnsPtnr> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(BpBnsPtnrSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
