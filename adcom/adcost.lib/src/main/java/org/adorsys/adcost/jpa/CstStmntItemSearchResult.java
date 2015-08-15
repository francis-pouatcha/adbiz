package org.adorsys.adcost.jpa;

import java.util.List;

import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchResult;

public class CstStmntItemSearchResult extends
		CoreAbstBsnsItemSearchResult<CstStmntItem> {

	public CstStmntItemSearchResult() {
		super();
	}

	public CstStmntItemSearchResult(Long count, List<CstStmntItem> resultList,
			CoreAbstBsnsItemSearchInput<CstStmntItem> searchInput) {
		super(count, resultList, searchInput);
	}
}
