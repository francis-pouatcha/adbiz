package org.adorsys.adcatal.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;

@XmlRootElement
public class CatalArtDetailConfigSearchResult extends CoreAbstIdentifObjectSearchResult<CatalArtDetailConfig, CatalArtDetailConfigSearchInput>{

	public CatalArtDetailConfigSearchResult() {
		super();
	}

	public CatalArtDetailConfigSearchResult(Long count,
			List<CatalArtDetailConfig> resultList,
			CatalArtDetailConfigSearchInput searchInput) {
		super(count, resultList, searchInput);
	}
	
}
