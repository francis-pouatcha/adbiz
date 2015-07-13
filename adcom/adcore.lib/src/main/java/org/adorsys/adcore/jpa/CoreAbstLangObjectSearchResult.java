package org.adorsys.adcore.jpa;

import java.util.List;

public abstract class CoreAbstLangObjectSearchResult<E extends CoreAbstLangObject> extends CoreAbstIdentifObjectSearchResult<E>{

	public CoreAbstLangObjectSearchResult() {
		super();
	}

	public CoreAbstLangObjectSearchResult(Long count, List<E> resultList,
			CoreAbstLangObjectSearchInput<E> searchInput) {
		super(count, resultList, searchInput);
	}
}
