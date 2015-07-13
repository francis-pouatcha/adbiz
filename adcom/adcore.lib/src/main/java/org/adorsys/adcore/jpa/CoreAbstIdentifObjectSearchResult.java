package org.adorsys.adcore.jpa;

import java.util.List;

public abstract class CoreAbstIdentifObjectSearchResult<E extends CoreAbstIdentifObject> extends CoreSearchResult<E> {

	public CoreAbstIdentifObjectSearchResult() {
	}

	public CoreAbstIdentifObjectSearchResult(Long count, List<E> resultList,
			CoreAbstIdentifObjectSearchInput<E> searchInput) {
		super(count, resultList, searchInput);
	}
}
