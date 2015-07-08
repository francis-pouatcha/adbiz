package org.adorsys.adcshdwr.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CdrDsPymntItemSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<CdrDsPymntItem> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private CdrDsPymntItemSearchInput searchInput;

   public CdrDsPymntItemSearchResult()
   {
      super();
   }

   public CdrDsPymntItemSearchResult(Long count, List<CdrDsPymntItem> resultList,
         CdrDsPymntItemSearchInput searchInput)
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

   public List<CdrDsPymntItem> getResultList()
   {
      return resultList;
   }

   public CdrDsPymntItemSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<CdrDsPymntItem> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(CdrDsPymntItemSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
