package org.adorsys.adcatal.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchInput;
import org.adorsys.adcore.jpa.CoreAbstIdentifObjectSearchResult;

@XmlRootElement
public class CatalArtDetailConfigSearchResult extends CoreAbstIdentifObjectSearchResult<CatalArtDetailConfig>{

	public CatalArtDetailConfigSearchResult() {
		super();
	}

	public CatalArtDetailConfigSearchResult(Long count, Long total,
			List<CatalArtDetailConfig> resultList,
			CoreAbstIdentifObjectSearchInput<CatalArtDetailConfig> searchInput) {
		super(count, total, resultList, searchInput);
	}
	
}
