package org.adorsys.adcshdwr.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CdrPymntSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<CdrPymnt> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private CdrPymntSearchInput searchInput;

   public CdrPymntSearchResult()
   {
      super();
   }

   public CdrPymntSearchResult(Long count, List<CdrPymnt> resultList,
         CdrPymntSearchInput searchInput)
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

   public List<CdrPymnt> getResultList()
   {
      return resultList;
   }

   public CdrPymntSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<CdrPymnt> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(CdrPymntSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
