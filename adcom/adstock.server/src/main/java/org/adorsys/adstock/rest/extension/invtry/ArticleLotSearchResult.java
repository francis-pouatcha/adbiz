/**
 * 
 */
package org.adorsys.adstock.rest.extension.invtry;

import java.util.List;

import org.adorsys.adstock.jpa.StkArticleLotSearchInput;

/**
 * @author boriswaguia
 *
 */
public class ArticleLotSearchResult {
	/**
	 * The articleLotSearchInput field.
	 */
	private StkArticleLotSearchInput articleLotSearchInput;
	/**
	 * The resultList field.
	 */
	private List<StkArticleLotDTO> resultList;
	/**
	 * The count field.
	 */
	private Long count;
	/**
	 * The searchInput field.
	 */
	private ArtLotSearchInput searchInput;
	
	public ArticleLotSearchResult(Long count, List<StkArticleLotDTO> resultList,ArtLotSearchInput searchInput)
	{
		super();
		this.count = count;
		this.searchInput = searchInput;
		this.resultList = resultList;
	}
	public StkArticleLotSearchInput getArticleLotSearchInput() {
		return articleLotSearchInput;
	}
	public void setArticleLotSearchInput(
			StkArticleLotSearchInput articleLotSearchInput) {
		this.articleLotSearchInput = articleLotSearchInput;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public ArtLotSearchInput getSearchInput() {
		return searchInput;
	}
	public void setSearchInput(ArtLotSearchInput searchInput) {
		this.searchInput = searchInput;
	}
	public List<StkArticleLotDTO> getResultList() {
		return resultList;
	}
	public void setResultList(List<StkArticleLotDTO> resultList) {
		this.resultList = resultList;
	}
	
}
