package org.adorsys.adcost.jpa;

import java.util.List;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

public class CstBearerSearchResult extends CoreAbstIdentifObjectSearchResult<CstBearer>{

	public CstBearerSearchResult() {
		super();
	}

	public CstBearerSearchResult(Long count, Long total,
			List<CstBearer> resultList, CoreSearchInput<CstBearer> searchInput) {
		super(count, total, resultList, searchInput);
	}
}
