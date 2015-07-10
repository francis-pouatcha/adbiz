package org.adorsys.adcatal.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;

@XmlRootElement
public class CatalProdFmlyLangMapSearchResult
		extends
		CoreAbstIdentifObjectSearchResult<CatalProdFmlyLangMap, CatalProdFmlyLangMapSearchInput> {

	public CatalProdFmlyLangMapSearchResult() {
		super();
	}

	public CatalProdFmlyLangMapSearchResult(Long count,
			List<CatalProdFmlyLangMap> resultList,
			CatalProdFmlyLangMapSearchInput searchInput) {
		super(count, resultList, searchInput);
	}
}
