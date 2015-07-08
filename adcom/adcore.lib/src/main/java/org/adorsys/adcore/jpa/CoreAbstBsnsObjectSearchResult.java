package org.adorsys.adcore.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public abstract class CoreAbstBsnsObjectSearchResult<E extends CoreAbstBsnsObject, I extends CoreAbstBsnsObjectSearchInput<E>>
		extends CoreAbstIdentifObjectSearchResult<E, I> {

	public CoreAbstBsnsObjectSearchResult() {
		super();
	}

	public CoreAbstBsnsObjectSearchResult(Long count, List<E> resultList,
			I searchInput) {
		super(count, resultList, searchInput);
	}
	
}
