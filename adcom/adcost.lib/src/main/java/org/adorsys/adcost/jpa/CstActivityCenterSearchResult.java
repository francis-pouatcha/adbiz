package org.adorsys.adcost.jpa;

import java.util.List;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

public class CstActivityCenterSearchResult extends CoreAbstIdentifObjectSearchResult<CstActivityCenter>{

	public CstActivityCenterSearchResult() {
		super();
	}

	public CstActivityCenterSearchResult(Long count, Long total,
			List<CstActivityCenter> resultList,
			CoreSearchInput<CstActivityCenter> searchInput) {
		super(count, total, resultList, searchInput);
	}
}
