package org.adorsys.adstock.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

@XmlRootElement
public class StkArticleLotSearchResult extends CoreAbstBsnsItemSearchResult<StkArticleLot>{

	public StkArticleLotSearchResult() {
		super();
	}

	public StkArticleLotSearchResult(Long count,
			List<StkArticleLot> resultList,
			CoreAbstBsnsItemSearchInput<StkArticleLot> searchInput) {
		super(count, resultList, searchInput);
	}

	public StkArticleLotSearchResult(Long count, Long total,
			List<StkArticleLot> resultList,
			CoreSearchInput<StkArticleLot> searchInput) {
		super(count, total, resultList, searchInput);
	}
	
	
	
}
