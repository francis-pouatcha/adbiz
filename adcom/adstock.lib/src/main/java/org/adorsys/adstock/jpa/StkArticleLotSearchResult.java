package org.adorsys.adstock.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchResult;

@XmlRootElement
public class StkArticleLotSearchResult extends CoreAbstBsnsItemSearchResult<StkArticleLot, CoreAbstBsnsItemSearchInput<StkArticleLot>>{

	public StkArticleLotSearchResult() {
		super();
	}

	public StkArticleLotSearchResult(Long count,
			List<StkArticleLot> resultList,
			CoreAbstBsnsItemSearchInput<StkArticleLot> searchInput) {
		super(count, resultList, searchInput);
	}
	
}
