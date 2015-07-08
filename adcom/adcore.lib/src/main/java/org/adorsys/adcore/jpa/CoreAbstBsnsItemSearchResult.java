package org.adorsys.adcore.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public abstract class CoreAbstBsnsItemSearchResult<E extends CoreAbstBsnsItem, I extends CoreAbstBsnsItemSearchInput<E>>
 extends CoreAbstIdentifObjectSearchResult<E,I>{

	public CoreAbstBsnsItemSearchResult() {
		super();
	}

	public CoreAbstBsnsItemSearchResult(Long count, List<E> resultList,
			I searchInput) {
		super(count, resultList, searchInput);
	}
	
}
