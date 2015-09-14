package org.adorsys.adstock.jpa;

import java.util.List;

import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchResult;
import org.adorsys.adcore.jpa.CoreSearchInput;

public class StkMvntSearchResult extends CoreAbstBsnsItemSearchResult<StkMvnt>
{

	public StkMvntSearchResult() {
		super();
	}

	public StkMvntSearchResult(Long count, List<StkMvnt> resultList,
			CoreAbstBsnsItemSearchInput<StkMvnt> searchInput) {
		super(count, resultList, searchInput);
	}

	public StkMvntSearchResult(Long count, Long total,
			List<StkMvnt> resultList, CoreSearchInput<StkMvnt> searchInput) {
		super(count, total, resultList, searchInput);
	}
	
	
}
