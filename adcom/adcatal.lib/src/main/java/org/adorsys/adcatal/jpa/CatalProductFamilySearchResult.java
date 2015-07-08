package org.adorsys.adcatal.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;

@XmlRootElement
public class CatalProductFamilySearchResult
		extends
		CoreAbstIdentifObjectSearchResult<CatalProductFamily, CatalProductFamilySearchInput> {

	public CatalProductFamilySearchResult() {
		super();
	}

	public CatalProductFamilySearchResult(Long count,
			List<CatalProductFamily> resultList,
			CatalProductFamilySearchInput searchInput) {
		super(count, resultList, searchInput);
	}
}
