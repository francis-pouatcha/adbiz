package org.adorsys.adcatal.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;

@XmlRootElement
public class CatalProdFmlySearchResult
		extends
		CoreAbstIdentifObjectSearchResult<CatalProdFmly, CatalProdFmlySearchInput> {

	public CatalProdFmlySearchResult() {
		super();
	}

	public CatalProdFmlySearchResult(Long count,
			List<CatalProdFmly> resultList,
			CatalProdFmlySearchInput searchInput) {
		super(count, resultList, searchInput);
	}
}
