package org.adorsys.adstock.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StkLotStockQtySearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<StkLotStockQty> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private StkLotStockQtySearchInput searchInput;

   public StkLotStockQtySearchResult()
   {
      super();
   }

   public StkLotStockQtySearchResult(Long count, List<StkLotStockQty> resultList,
         StkLotStockQtySearchInput searchInput)
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

   public List<StkLotStockQty> getResultList()
   {
      return resultList;
   }

   public StkLotStockQtySearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<StkLotStockQty> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(StkLotStockQtySearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
