package org.adorsys.adcost.jpa;

import java.util.List;

import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

public class CstStmntItemSearchResult extends
		CoreAbstBsnsItemSearchResult<CstStmntItem> {

	public CstStmntItemSearchResult() {
		super();
	}

	public CstStmntItemSearchResult(Long count, List<CstStmntItem> resultList,
			CoreAbstBsnsItemSearchInput<CstStmntItem> searchInput) {
		super(count, resultList, searchInput);
	}

	public CstStmntItemSearchResult(Long count, Long total,
			List<CstStmntItem> resultList,
			CoreSearchInput<CstStmntItem> searchInput) {
		super(count, total, resultList, searchInput);
	}
	
	
}
