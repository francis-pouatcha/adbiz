package org.adorsys.adcatal.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;

@XmlRootElement
public class CatalArtManufSuppSearchResult
		extends
		CoreAbstIdentifObjectSearchResult<CatalArtManufSupp, CatalArtManufSuppSearchInput> {

	public CatalArtManufSuppSearchResult() {
		super();
	}

	public CatalArtManufSuppSearchResult(Long count,
			List<CatalArtManufSupp> resultList,
			CatalArtManufSuppSearchInput searchInput) {
		super(count, resultList, searchInput);
	}
}
