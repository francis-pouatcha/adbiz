package org.adorsys.adcatal.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;

@XmlRootElement
public class CatalArtEquivalenceSearchResult extends CoreAbstIdentifObjectSearchResult<CatalArtEquivalence, CatalArtEquivalenceSearchInput>
{

	public CatalArtEquivalenceSearchResult() {
		super();
	}

	public CatalArtEquivalenceSearchResult(Long count,
			List<CatalArtEquivalence> resultList,
			CatalArtEquivalenceSearchInput searchInput) {
		super(count, resultList, searchInput);
	}
}
