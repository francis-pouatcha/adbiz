package org.adorsys.adbnsptnr.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BpInsrrncPpseSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<BpInsrrncPpse> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private BpInsrrncPpseSearchInput searchInput;

   public BpInsrrncPpseSearchResult()
   {
      super();
   }

   public BpInsrrncPpseSearchResult(Long count, List<BpInsrrncPpse> resultList,
         BpInsrrncPpseSearchInput searchInput)
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

   public List<BpInsrrncPpse> getResultList()
   {
      return resultList;
   }

   public BpInsrrncPpseSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<BpInsrrncPpse> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(BpInsrrncPpseSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
