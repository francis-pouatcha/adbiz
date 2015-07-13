package org.adorsys.adcatal.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;

@XmlRootElement
public class CatalArticleSearchResult extends CoreAbstIdentifObjectSearchResult<CatalArticle> {

	public CatalArticleSearchResult() {
		super();
	}

	public CatalArticleSearchResult(Long count, List<CatalArticle> resultList,
			CoreAbstIdentifObjectSearchInput<CatalArticle> searchInput) {
		super(count, resultList, searchInput);
	}
}
