package org.adorsys.adcshdwr.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchInput;
import org.adorsys.adcore.jpa.CoreAbstBsnsItemSearchResult;

@XmlRootElement
public class CdrDsArtItemSearchResult extends CoreAbstBsnsItemSearchResult<CdrDsArtItem>{

	public CdrDsArtItemSearchResult() {
		super();
	}

	public CdrDsArtItemSearchResult(Long count, List<CdrDsArtItem> resultList,
			CoreAbstBsnsItemSearchInput<CdrDsArtItem> searchInput) {
		super(count, resultList, searchInput);
	}
	
}
