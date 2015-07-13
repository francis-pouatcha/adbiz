package org.adorsys.adcatal.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;

@XmlRootElement
public class CatalArtManufSuppSearchResult
		extends
		CoreAbstIdentifObjectSearchResult<CatalArtManufSupp> {

	public CatalArtManufSuppSearchResult() {
		super();
	}

	public CatalArtManufSuppSearchResult(Long count,
			List<CatalArtManufSupp> resultList,
			CoreAbstIdentifObjectSearchInput<CatalArtManufSupp> searchInput) {
		super(count, resultList, searchInput);
	}
}
