package org.adorsys.adcshdwr.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CdrPymntItemSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<CdrPymntItem> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private CdrPymntItemSearchInput searchInput;

   public CdrPymntItemSearchResult()
   {
      super();
   }

   public CdrPymntItemSearchResult(Long count, List<CdrPymntItem> resultList,
         CdrPymntItemSearchInput searchInput)
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

   public List<CdrPymntItem> getResultList()
   {
      return resultList;
   }

   public CdrPymntItemSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<CdrPymntItem> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(CdrPymntItemSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
