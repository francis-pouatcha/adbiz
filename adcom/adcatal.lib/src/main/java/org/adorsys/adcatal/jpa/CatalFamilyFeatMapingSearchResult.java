package org.adorsys.adcatal.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;

@XmlRootElement
public class CatalFamilyFeatMapingSearchResult
		extends
		CoreAbstIdentifObjectSearchResult<CatalFamilyFeatMaping, CatalFamilyFeatMapingSearchInput> {

	public CatalFamilyFeatMapingSearchResult() {
		super();
	}

	public CatalFamilyFeatMapingSearchResult(Long count,
			List<CatalFamilyFeatMaping> resultList,
			CatalFamilyFeatMapingSearchInput searchInput) {
		super(count, resultList, searchInput);
	}
}
