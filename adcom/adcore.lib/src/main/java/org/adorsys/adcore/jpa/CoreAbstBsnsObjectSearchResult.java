package org.adorsys.adcore.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public abstract class CoreAbstBsnsObjectSearchResult<E extends CoreAbstBsnsObject>
		extends CoreAbstIdentifObjectSearchResult<E> {

	public CoreAbstBsnsObjectSearchResult() {
		super();
	}

	public CoreAbstBsnsObjectSearchResult(Long count, List<E> resultList,
			CoreAbstBsnsObjectSearchInput<E> searchInput) {
		super(count, resultList, searchInput);
	}
	
}
