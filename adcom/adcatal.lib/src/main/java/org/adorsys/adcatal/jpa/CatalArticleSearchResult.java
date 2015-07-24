package org.adorsys.adcatal.jpa;

import java.util.List;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;

public class CatalArticleSearchResult extends CoreAbstIdentifObjectSearchResult<CatalArticle> {

	public CatalArticleSearchResult() {
		super();
	}

	public CatalArticleSearchResult(Long count, Long total, List<CatalArticle> resultList,
			CoreAbstIdentifObjectSearchInput<CatalArticle> searchInput) {
		super(count, total, resultList, searchInput);
	}
}
