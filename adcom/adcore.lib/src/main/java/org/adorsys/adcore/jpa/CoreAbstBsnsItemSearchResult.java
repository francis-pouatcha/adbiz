package org.adorsys.adcore.jpa;

import java.util.List;

public abstract class CoreAbstBsnsItemSearchResult<E extends CoreAbstBsnsItem>
 extends CoreAbstIdentifObjectSearchResult<E>{

	public CoreAbstBsnsItemSearchResult() {
		super();
	}

	public CoreAbstBsnsItemSearchResult(Long count, Long total, List<E> resultList,
			CoreAbstBsnsItemSearchInput<E> searchInput) {
		super(count, total, resultList, searchInput);
	}
	
}
