package org.adorsys.adcshdwr.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CdrPymntObjectSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<CdrPymntObject> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private CdrPymntObjectSearchInput searchInput;

   public CdrPymntObjectSearchResult()
   {
      super();
   }

   public CdrPymntObjectSearchResult(Long count, List<CdrPymntObject> resultList,
         CdrPymntObjectSearchInput searchInput)
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

   public List<CdrPymntObject> getResultList()
   {
      return resultList;
   }

   public CdrPymntObjectSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<CdrPymntObject> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(CdrPymntObjectSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
