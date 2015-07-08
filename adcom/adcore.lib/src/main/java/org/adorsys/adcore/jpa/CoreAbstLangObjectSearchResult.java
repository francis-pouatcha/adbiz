package org.adorsys.adcore.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public abstract class CoreAbstLangObjectSearchResult<E extends CoreAbstLangObject, I extends CoreAbstLangObjectSearchInput<E>> extends CoreAbstIdentifObjectSearchResult<E,I>{

	public CoreAbstLangObjectSearchResult() {
		super();
	}

	public CoreAbstLangObjectSearchResult(Long count, List<E> resultList,
			I searchInput) {
		super(count, resultList, searchInput);
	}
}
