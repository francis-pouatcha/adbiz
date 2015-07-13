package org.adorsys.adcatal.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;

@XmlRootElement
public class CatalArtEquivalenceSearchResult extends CoreAbstIdentifObjectSearchResult<CatalArtEquivalence>
{

	public CatalArtEquivalenceSearchResult() {
		super();
	}

	public CatalArtEquivalenceSearchResult(Long count,
			List<CatalArtEquivalence> resultList,
			CoreAbstIdentifObjectSearchInput<CatalArtEquivalence> searchInput) {
		super(count, resultList, searchInput);
	}
}
