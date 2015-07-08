package org.adorsys.adstock.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StkLotRsvQtySearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<StkLotRsvQty> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private StkLotRsvQtySearchInput searchInput;

   public StkLotRsvQtySearchResult()
   {
      super();
   }

   public StkLotRsvQtySearchResult(Long count, List<StkLotRsvQty> resultList,
		   StkLotRsvQtySearchInput searchInput)
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

   public List<StkLotRsvQty> getResultList()
   {
      return resultList;
   }

   public StkLotRsvQtySearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<StkLotRsvQty> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(StkLotRsvQtySearchInput searchInput)
   {
      this.searchInput = searchInput;
   }
}
