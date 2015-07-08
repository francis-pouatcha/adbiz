package org.adorsys.adsales.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SlsInvoiceSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<SlsInvoice> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private SlsInvoiceSearchInput searchInput;

   public SlsInvoiceSearchResult()
   {
      super();
   }

   public SlsInvoiceSearchResult(Long count, List<SlsInvoice> resultList,
         SlsInvoiceSearchInput searchInput)
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

   public List<SlsInvoice> getResultList()
   {
      return resultList;
   }

   public SlsInvoiceSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<SlsInvoice> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(SlsInvoiceSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
