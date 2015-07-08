package org.adorsys.adbnsptnr.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BpIndivPtnrNameSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<BpIndivPtnrName> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private BpIndivPtnrNameSearchInput searchInput;

   public BpIndivPtnrNameSearchResult()
   {
      super();
   }

   public BpIndivPtnrNameSearchResult(Long count, List<BpIndivPtnrName> resultList,
         BpIndivPtnrNameSearchInput searchInput)
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

   public List<BpIndivPtnrName> getResultList()
   {
      return resultList;
   }

   public BpIndivPtnrNameSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<BpIndivPtnrName> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(BpIndivPtnrNameSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
